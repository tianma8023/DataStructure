package tianma.learn.ds.search;

/**
 * 斐波那契查找,时间复杂度O(logn),平均性能优于折半查找
 * <p>
 * 斐波那契查找就是在二分查找的基础上根据斐波那契数列进行分割的。在斐波那契数列找一个等于略大于查找表中元素个数的数F[n]，将原查找表扩展为长度为F[n](
 * 如果要补充元素，则补充重复最后一个元素，直到满足F[n]个元素)，完成后进行斐波那契分割，即F[n]个元素分割为前半部分F[n-1]个元素，后半部分F[n
 * -2]个元素，找出要查找的元素在那一部分并递归，直到找到。 <br>
 * 参考链接:
 * <a href="http://my.oschina.net/u/140462/blog/282319">斐波那契查找（黄金分割法查找）</a><br>
 * 
 * @author Tianma
 *
 */
public class FibonacciSearcher implements Searcher {

	private static final int MAX_ARRAY_SIZE = 30;

	/**
	 * 得到长度为len的斐波那契数列
	 * 
	 * @return
	 */
	private int[] fibonacci(int len) {
		if (len < 0)
			throw new IllegalArgumentException("length must bigger than 0");
		int[] fibonacci = new int[len];
		fibonacci[0] = 1;
		fibonacci[1] = 1;
		for (int i = 2; i < len; i++) {
			fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
		}
		return fibonacci;
	}

	@Override
	public int search(int[] array, int key) {
		int low = 0; // 低位
		int len = array.length;
		int high = len - 1; // 高位
		int mid; // 中间位
		int k = 0; // 斐波那契数列下标(用于进行分割)

		// 获取斐波那契数列
		int[] fib = fibonacci(MAX_ARRAY_SIZE);

		// 获取斐波那契数列分割点位置
		while (len > fib[k] - 1) {
			k++;
		}

		// 创建临时数组(数组长度为fib[k] - 1)
		int[] tmp = new int[fib[k] - 1];
		// 拷贝原数组到tmp数组中
		System.arraycopy(array, 0, tmp, 0, len);
		// 填充tmp数组中剩余的位置,补充的元素值为最后一个元素值
		for (int i = len; i < fib[k] - 1; i++) {
			tmp[i] = array[high];
		}

		// 开始进行类似于二分查找的查找
		while (low <= high) {
			// 对于tmp数组,整个数组的长度为fib[k]-1
			// 而 fib[k]-1 = (fib[k-1]-1) + 1 + (fib[k-2]-1);
			// 所以可以这样理解： mid下标对应元素可以将整个数组拆分为两部分,第1部分有fib[k-1]-1个元素,第2部分有fib[k-2]-1个元素
			// mid=low+fib[k-1]-1; 正是将 数组的[low, max(high,tmp.length-1)]
			// 部分按照斐波那契规则分为两部分
			mid = low + fib[k - 1] - 1;
			if (tmp[mid] > key) {
				// 需要查找第1部分
				high = mid - 1;
				// fib[k] = fib[k-1] + fib[k-2]
				// 第一部分有fib[k-1]个元素,所以将k-1赋值为k
				k = k - 1;
			} else if (tmp[mid] < key) {
				// 需要查找第2部分
				low = mid + 1;
				// fib[k] = fib[k-1] + fib[k-2]
				// 第二部分有fib[k-2]个元素,所以将k-2赋值给k
				k = k - 2;
			} else {
				// 查找成功
				// 一下代码其实就是返回 min(mid, high);
				// return Math.min(mid, high);
				if (mid <= high)
					return mid;
				else
					return high; // 因为mid可能大于high,即查找到了补充的元素,那么还是应该返回high
			}
		}

		return -1;
	}

}

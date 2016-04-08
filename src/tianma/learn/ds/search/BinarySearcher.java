package tianma.learn.ds.search;

/**
 * 二分法查找,时间复杂度O(logn)
 * 
 * @author Tianma
 *
 */
public class BinarySearcher implements Searcher {

	// 二分法查找前提,查找表array是顺序(这里要求递增)排列的
	@Override
	public int search(int[] array, int key) {
		int low, high, mid;
		low = 0; // 定义最低下标为array首位
		high = array.length - 1; // 定义最高下标为array末位
		while (low <= high) {
			mid = (low + high) / 2; // 折半
			if (array[mid] > key) {
				// 中值比key大,则high=mid-1
				high = mid - 1;
			} else if (array[mid] < key) {
				// 中值比key小,则low=mid+1
				low = mid + 1;
			} else {
				// 相等说明mid即为key在array中所在位置
				return mid;
			}
		}
		return -1;
	}

}

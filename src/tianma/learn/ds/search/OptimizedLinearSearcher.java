package tianma.learn.ds.search;

import java.util.Arrays;

/**
 * 优化的顺序表查找,时间复杂度O(n),但是比普通顺序表查找效率高
 * 
 * @author Tianma
 *
 */
public class OptimizedLinearSearcher implements Searcher {

	// 相比单纯的线性查找每次for循环需要判断两次,这里设置关键字值(即哨兵)
	// 当数据量比较大时,如果单纯从线性查找角度看,优化后的线性搜索优势明显
	@Override
	public int search(int[] array, int key) {
		int len = array.length;
		if (len == 0) // array为空,返回-1
			return -1;
		// 为了避免对array进行修改,在这里拷贝一份(这一步需不需要是根据实际需求定的)
		int[] arr = Arrays.copyOf(array, array.length);
		if (arr[0] == key)
			return 0;
		arr[0] = key; // array[0]不是key,那么将key赋值给array[0],将array[0]作为哨兵
		// 这里"哨兵"也可以放在数组尾部
		int i = len - 1;
		while (arr[i] != key) { // 每次循环少判断一个
			i--;
		}
		if (i == 0) // 从数组尾部一直查找到array[0]才找到,说明不存在
			return -1;
		return i;
	}

}

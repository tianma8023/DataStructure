package tianma.learn.ds.search;

/**
 * 顺序表查找,时间复杂度为O(n)
 * 
 * @author Tianma
 *
 */
public class LinearSearcher implements Searcher {

	// 标准的线性查找,这里有缺陷：在循环中每个循环实际上需要判断两次(一次是否相等,一次是否越界)
	// 改进方法参考OptimizedLinearSearcher
	@Override
	public int search(int[] array, int key) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == key)
				return i;
		}
		return -1;
	}

}

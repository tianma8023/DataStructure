package tianma.learn.ds.sort;

/**
 * 冒泡排序，时间复杂度O(n^2)
 * <p>
 * 冒泡排序是一种交换排序，它的基本思想是：两两比较相邻记录的关键字，如果反序则交换，直至没有反序的记录为止
 * 
 * @author Tianma
 *
 */
public class BubbleSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		bubbleSort(arr);
		return arr;
	}
	
	protected void bubbleSort(int[] arr) {
		int i, j;
		int len = arr.length;
		for (i = 0; i < len - 1; i++) {
			for (j = len - 1; j > i; j--) {
				if (arr[j - 1] > arr[j]) {
					swap(arr, j - 1, j);
				}
			}
		}
	}

}

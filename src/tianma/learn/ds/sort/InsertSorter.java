package tianma.learn.ds.sort;

/**
 * 插入排序（或称为直接插入排序），算法原理是将一个记录插入到之前已经排好序的有序表中，从而得到新的、记录数增加1的有序表
 * <p>
 * 插入排序时间复杂度仍为O(n^2),但其性能要优于冒泡排序和选择排序
 * 
 * @author Tianma
 *
 */
public class InsertSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		insertSort(arr);
		return arr;
	}

	private void insertSort(int[] arr) {
		int len = arr.length;
		int i, j;
		int tmp;
		for (i = 1; i < len; i++) { // 从下标1开始遍历,因为下标0的已经排好序
			if (arr[i] < arr[i - 1]) {
				// 如果当前下标对应的记录小于前一位记录,则需要插入,否则不需要插入，直接将记录数增加1
				tmp = arr[i]; // 记录下标i对应的元素
				for (j = i - 1; j >= 0 && arr[j] > tmp; j--) {
					arr[j + 1] = arr[j]; // 记录后移
				}
				arr[j + 1] = tmp; // 插入正确位置
			}
		}
	}

}

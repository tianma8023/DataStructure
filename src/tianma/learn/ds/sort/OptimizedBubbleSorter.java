package tianma.learn.ds.sort;

/**
 * 优化后的冒泡排序，性能优于普通冒泡排序，时间复杂度仍为O(n^2)
 * 
 * @author Tianma
 *
 */
public class OptimizedBubbleSorter extends BubbleSorter {

	@Override
	protected void bubbleSort(int[] arr) {
		int i, j;
		int len = arr.length;
		boolean swapped = true; // 标记：上一趟循环是否进行过交换
		for (i = 0; i < len - 1 && swapped; i++) {
			swapped = false;
			// 在某一趟遍历中,如果没有可交换的,那么swapped = false,跳出外层循环;
			// 如果一旦进行了交换,则swapped = true,需要进行下一趟遍历
			for (j = len - 1; j > i; j--) {
				if (arr[j - 1] > arr[j]) {
					swap(arr, j - 1, j);
					swapped = true;
				}
			}
		}
	}

}

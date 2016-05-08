package tianma.learn.ds.sort;

/**
 * 快速排序的优化:<br>
 * 1. 优化排序基准(枢轴pivot)的选取：(1)选取第一个，(2)随机选取，(3)三值取中：最佳为第三个<br>
 * 2. 优化小数组时的排序方案：当局部排序数组长度较小时，采用插入排序，而非快速排序，因为长度分割到够小后，继续分割的效率要低于直接插入排序<br>
 * 3. 优化不必要的交换：将交换改为替换<br>
 */
public class OptimizedQuickSorter extends QuickSorter {

	/**
	 * 插入排序最大数组长度值
	 */
	private static final int MAX_LENGTH_INSERT_SORT = 7;

	/**
	 * 对数组arr[low...high]的子序列作快速排序，使之有序
	 */
	@Override
	protected void quickSort(int[] arr, int low, int high) {
		int pivotLoc; // 记录枢轴(pivot)所在位置
		if ((high - low + 1) > MAX_LENGTH_INSERT_SORT) {
			// 待排序数组长度大于临界值，则进行快速排序
			pivotLoc = partition(arr, low, high); // 将arr[low...high]一分为二,并返回枢轴位置

			quickSort(arr, low, pivotLoc - 1);// 递归遍历arr[low...pivotLoc-1]
			quickSort(arr, pivotLoc + 1, high); // 递归遍历arr[pivotLoc+1...high]
		} else {
			// 2. 优化小数组时的排序方案，将快速排序改为插入排序
			insertSort(arr, low, high); // 对arr[low...high]子序列进行插入排序
		}
	}

	/**
	 * 在arr[low...high]中利用三值取中选取枢轴(pivot)，将arr[low...high]分成两部分，
	 * 前半部分的子序列的记录均小于pivot，后半部分的记录均大于pivot;最后返回pivot的位置
	 */
	@Override
	protected int partition(int[] arr, int low, int high) {
		int pivot;
		pivot = medianOfThree(arr, low, high); // 1. 优化排序基准，使用三值取中获取中值
		while (low < high) { // 从数组的两端向中间扫描
			while (low < high && arr[high] >= pivot) {
				high--;
			}
			// swap(arr, low, high); // 将比枢轴pivot小的元素交换到低位
			arr[low] = arr[high]; // 3. 优化不必要的交换，使用替换而不是交换
			while (low < high && arr[low] <= pivot) {
				low++;
			}
			// swap(arr, low, high); // 将比枢轴pivot大的元素交换到高位
			arr[high] = arr[low]; // 3. 优化不必要的交换，使用替换而不是交换
		}
		arr[low] = pivot;
		return low; // 返回一趟下来后枢轴pivot所在的位置
	}

	/**
	 * 通过三值取中(从arr[low...high]子序列中)获取枢轴pivot的值，让arr[low]变成中值;并返回计算的枢轴(pivot)
	 */
	private int medianOfThree(int[] arr, int low, int high) {
		int mid = low + ((high - low) >> 1); // mid = low + (high-low)/2, 中间元素下标

		// 使用三值取中得到枢轴
		if (arr[low] > arr[high]) { // 目的：让arr[low] <= arr[high]
			swap(arr, low, high);
		}
		if (arr[mid] > arr[high]) { // 目的：让arr[mid] <= arr[high]
			swap(arr, mid, high);
		}
		if (arr[mid] > arr[low]) { // 目的： 让arr[low] >= arr[mid]
			swap(arr, low, mid);
		}
		// 经过上述变化，最终 arr[mid]<=arr[low]<=arr[high]，则arr[low]为中间值
		return arr[low];
	}

	/**
	 * 对子序列arr[low...high]进行插入排序
	 * 
	 * @param arr
	 *            需要进行部分插入排序的数组
	 * @param low
	 *            需要进行插入排序的起始位置
	 * @param high
	 *            需要进行插入排序的结束位置
	 */
	private void insertSort(int[] arr, int low, int high) {
		int i, j;
		int tmp;
		for (i = low + 1; i <= high; i++) { // 从下标low+1开始遍历,因为下标为low的已经排好序
			if (arr[i] < arr[i - 1]) {
				// 如果当前下标对应的记录小于前一位记录,则需要插入,否则不需要插入，直接将记录数增加1
				tmp = arr[i]; // 记录下标i对应的元素
				for (j = i - 1; j >= low && arr[j] > tmp; j--) {
					arr[j + 1] = arr[j]; // 记录后移
				}
				arr[j + 1] = tmp; // 插入正确位置
			}
		}
	}

}

package tianma.learn.ds.sort;

/**
 * 快速排序(Quick Sort)<br>
 * 基本思想： 通过一趟排序，将待排序记录分割成独立的两部分，启动一部分的关键字均比另一部分的关键字小，
 * 然后继续对这两部分记录继续进行排序，以达到整个序列有序 <br>
 * 
 * 
 * @author Tianma
 *
 */
public class QuickSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
		return arr;
	}

	/**
	 * 对数组arr[low...high]的子序列作快速排序，使之有序
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 */
	protected void quickSort(int[] arr, int low, int high) {
		int pivot;
		if (low < high) {
			pivot = partition(arr, low, high); // 将arr[low...high]一分为二,并计算中心值

			quickSort(arr, low, pivot - 1);// 递归遍历arr[low...pivot-1]
			quickSort(arr, pivot + 1, high); // 递归遍历arr[pivot+1...high]
		}
	}

	/**
	 * 在arr[low...high]选定pivot=arr[low]作为中点，将arr[low...high]分成两部分，
	 * 前半部分的子序列的记录均小于pivot，后半部分的记录均大于pivot
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 * @return
	 */
	protected int partition(int[] arr, int low, int high) {
		int pivot;
		pivot = arr[low]; // 将arr[low]作为中心值
		while (low < high) { // 从数组的两端向中间扫描
			while (low < high && arr[high] >= pivot) {
				high--;
			}
			swap(arr, low, high); // 将比中心值pivot小的元素交换到低位
			while (low < high && arr[low] <= pivot) {
				low++;
			}
			swap(arr, low, high); // 将比中心值pivot大的元素交换到高位
		}
		return low; // 返回一趟下来后pivot所在的位置
	}

}

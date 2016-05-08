package tianma.learn.ds.sort;

/**
 * 快速排序(Quick Sort)<br>
 * 基本思想： 通过一趟排序，将待排序记录分割成独立的两部分，其中一部分的关键字均比另一部分的关键字小，
 * 然后对这两部分记录继续进行递归排序，以达到整个序列有序 <br>
 * 时间复杂度：平均时间复杂度为O(nlogn)，最差的时间复杂度为O(n^2)<br>
 * 空间复杂度：平均空间复杂度为O(logn)，最差空间复杂度为O(n)<br>
 * 因为关键字的比较和交换是跳跃进行的，所以快速排序不是稳定的排序方法<br>
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
	 */
	protected void quickSort(int[] arr, int low, int high) {
		int pivotLoc; // 记录枢轴(pivot)所在位置
		if (low < high) {
			pivotLoc = partition(arr, low, high); // 将arr[low...high]一分为二,并返回枢轴位置

			quickSort(arr, low, pivotLoc - 1);// 递归遍历arr[low...pivotLoc-1]
			quickSort(arr, pivotLoc + 1, high); // 递归遍历arr[pivotLoc+1...high]
		}
	}

	/**
	 * 在arr[low...high]选定pivot=arr[low]作为枢轴（中间位置），将arr[low...high]分成两部分，
	 * 前半部分的子序列的记录均小于pivot，后半部分的记录均大于pivot;最后返回pivot的位置
	 */
	protected int partition(int[] arr, int low, int high) {
		int pivot;
		pivot = arr[low]; // 将arr[low]作为枢轴
		while (low < high) { // 从数组的两端向中间扫描
			while (low < high && arr[high] >= pivot) {
				high--;
			}
			swap(arr, low, high); // 将比枢轴pivot小的元素交换到低位
			while (low < high && arr[low] <= pivot) {
				low++;
			}
			swap(arr, low, high); // 将比枢轴pivot大的元素交换到高位
		}
		return low; // 返回一趟下来后枢轴pivot所在的位置
	}

}

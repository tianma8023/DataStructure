package tianma.learn.ds.sort;

/**
 * 归并排序（Merging Sort），采用的基本思想是归并（合并）思想，是一种比较占用内存，但效率高且稳定的算法
 * <p>
 * 原理：假设初始序列为n个记录，则可以看成n个有序的子序列，每个子序列长度为1，然后长度为1的子序列两两归并，得到⌈n/2⌉(⌈x⌉表示不小于
 * x的最小整数)个长度为2或者1的有序子序列， 然后再两两合并，如此重复，直至得到一个长度为n的有序序列为止。
 * <p>
 * 时间复杂度：O(nlogn)，其最好、最坏、平均性能均为O(nlogn);是一种稳定的排序算法;</br>
 * 空间复杂度：O(n+logn);
 * 
 * @author Tianma
 *
 */
public class MergeSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		int[] result = new int[arr.length];
		mergeSort(arr, result, 0, arr.length - 1);
		return result;
	}

	/**
	 * 将乱序的src[start...end]归并排序为有序的des[start...end]
	 * 
	 * @param src
	 *            归并前乱序数组
	 * @param des
	 *            归并后的有序数组
	 * @param start
	 *            归并的起始位置
	 * @param end
	 *            归并的终止位置
	 */
	private void mergeSort(int[] src, int[] des, int start, int end) {
		if (start == end) {
			des[start] = src[start];
			return;
		}
		int[] tmp = new int[src.length];
		// 将src[start...end]分为src[start...mid]和src[mid+1...end]两部分
		int mid = (start + end) / 2;
		mergeSort(src, tmp, start, mid); // 递归，将src[start...mid]归并为有序的tmp[start...mid]
		mergeSort(src, tmp, mid + 1, end); // 递归，将src[mid+1...end]归并为有序的tmp[mid+1...end]
		// 将有序的tmp[start...mid]和tmp[mid+1...end]合并为des[start...end]
		merge(tmp, des, start, mid, end);
	}

	/**
	 * 将有序的src[start, mid]和有序的src[mid+1, end]合并为有序的des[start,end];
	 * src可能为乱序数组,但是src[start, mid]和src[mid+1, end]是有序的。
	 * 
	 * @param src
	 *            乱序的原数组
	 * @param des
	 *            有序的目标数组
	 * @param start
	 *            数组第一部分起始位置
	 * @param mid
	 *            数组第一部分结束位置（两部分的分界点）
	 * @param end
	 *            数组第二部分结束位置
	 */
	protected void merge(int[] src, int[] des, int start, int mid, int end) {
		int i; // src数组第一部分下标
		int j; // src数组第二部分下标
		int k; // des数组下标
		// 将较小的数依次移动到目标数组中
		for (i = start, k = start, j = mid + 1; i <= mid && j <= end;) {
			if (src[i] < src[j]) {
				des[k] = src[i++];
			} else {
				des[k] = src[j++];
			}
			k++;
		}
		// 将剩余的src[i...mid]复制到des数组中
		for (; i <= mid; i++) {
			des[k] = src[i];
			k++;
		}

		// 将剩余的src[j...end]复制到des数组中
		for (; j <= end; j++) {
			des[k] = src[j];
			k++;
		}
	}

}

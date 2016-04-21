package tianma.learn.ds.sort;

/**
 * 归并排序的非递归实现
 * <p>
 * 空间复杂度为O(n)，性能优于递归实现的归并排序
 * 
 * @author Tianma
 *
 */
public class NonRecursiveMergeSorter extends MergeSorter {

	@Override
	public int[] sort(int[] arr) {
		mergeSort(arr);
		return arr;
	}

	private void mergeSort(int[] arr) {
		int len = arr.length;
		int result[] = new int[len];
		int k = 1;
		while (k < len) {
			mergePass(arr, result, k); // arr归并至result,此时间隔为k
			k = 2 * k; // 子序列长度加倍
			mergePass(result, arr, k); // result归并至arr,此时间隔翻倍
			k = 2 * k; // 子序列长度加倍
		}
	}

	/**
	 * 将数组src中相邻长度为interval的子序列两两归并到des数组中
	 * 
	 * @param src
	 *            源数组
	 * @param des
	 *            目标数组
	 * @param interval
	 *            两两合并的子序列长度
	 */
	private void mergePass(int[] src, int[] des, int interval) {
		int i = 0;
		int len = src.length;
		while (i + 2 * interval - 1 < len) {
			// 两两合并
			merge(src, des, i, i + interval - 1, i + 2 * interval - 1);
			i = i + 2 * interval;
		}
		if (i + interval - 1 < len - 1) {
			// i+interval-1小于len-1，说明最后还剩余两个子序列，只不过最后的一个子序列长度不够interval
			// 那么将剩下的两个子序列进行合并
			merge(src, des, i, i + interval - 1, len - 1);
		} else {
			// 否则，最后只剩下单个子序列，则直接将该子序列加入到des尾部
			for (; i < len; i++) {
				des[i] = src[i];
			}
		}
	}

}

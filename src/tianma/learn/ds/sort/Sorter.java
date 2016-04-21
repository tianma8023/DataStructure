package tianma.learn.ds.sort;

public interface Sorter {

	/**
	 * 将数组按升序排序
	 */
	int[] sort(int[] arr);

	/**
	 * 交换数组arr中的第i个位置和第j个位置的关键字
	 */
	default void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}

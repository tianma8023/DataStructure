package tianma.learn.ds.sort;

public interface Sorter {

	/**
	 * 将数组按升序排序
	 * 
	 * @param arr
	 */
	int[] sort(int[] arr);

	default void swap(int[] arr, int i, int j) {
		// arr[i] ^= arr[j];
		// arr[j] ^= arr[i];
		// arr[i] ^= arr[j];
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}

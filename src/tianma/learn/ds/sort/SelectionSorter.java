package tianma.learn.ds.sort;

/**
 * 选择排序（或者称为简单选择排序），n-i次关键字比较，从n-i+1个记录中选出关键字最小的记录，并和第i(0<=i<=n-1)个记录交换之
 * <p>
 * 其时间复杂度为O(n^2)，相较于冒泡排序，选择排序的数据交换次数少，所以性能优于冒泡排序
 * 
 * @author Tianma
 *
 */
public class SelectionSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		selectSort(arr);
		return arr;
	}

	private void selectSort(int[] arr) {
		int i, j, min;
		int len = arr.length;
		for (i = 0; i < len - 1; i++) {
			min = i; // min记录最小值的下标
			for (j = i + 1; j < len; j++) { // 循环i之后的数据
				if (arr[j] < arr[min]) // 发现有小于当前最小值的关键字
					min = j; // 将该下标赋值给min
			}
			if (i != min) // 如果i和min不等，在i之后的数据中找到了最小值，则需要arr[i]于arr[min]进行交换
				swap(arr, i, min);
		}
	}

}

package tianma.learn.ds.sort;

/**
 * 希尔排序
 * <p>
 * 希尔排序采取分割策略：将相距某个"增量"的记录组成一个子序列，保证在每个子序列内部分别进行插入排序后得到的结果是基本有序而不是局部有序
 * <p>
 * 该方法的基本思想是：先将整个待排元素序列分割成若干个子序列（由相隔某个“增量”的元素组成的）分别进行直接插入排序，然后依次缩减增量再进行排序，
 * 待整个序列中的元素基本有序（增量足够小）时，再对全体元素进行一次直接插入排序。<br>
 * 因为直接插入排序在元素基本有序的情况下（接近最好情况），效率是很高的，因此希尔排序在时间效率上比前两种方法有较大提高。
 * <p>
 * 当增量序列为： delta(k)=2^(t-k+1)-1 (0<=k<=t<=⌊log2(n+1)⌋)时，其时间复杂度为O(n^(3/2));
 * 优于插入排序
 * 
 * @author Tianma
 *
 */
public class ShellSorter implements Sorter {

	@Override
	public int[] sort(int[] arr) {
		shellSort(arr);
		return arr;
	}

	// Shell排序的关键就是增量序列的选取：
	// 增量序列原则： 增量序列的最后一个增量必须等于1
	private void shellSort(int[] arr) {
		int len = arr.length;
		int increment = len;
		for (; increment != 1;) {// 循环直至increment=1
			increment = increment / 3 + 1; // 计算增量序列(将整个序列分成increment组,在组内进行插入排序)
			for (int i = increment; i < len; i++) {
				if (arr[i] < arr[i - increment]) {
					int tmp = arr[i]; // 暂存arr[i]的值
					int j;
					for (j = i - increment; j >= 0 && tmp < arr[j]; j -= increment) {
						// 组内记录后移,查找插入位置
						arr[j + increment] = arr[j];
					}
					// 找到插入位置
					arr[j + increment] = tmp;
				}
			}
		}
	}

}

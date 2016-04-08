package tianma.learn.ds.search;

/**
 * 插值查找,时间复杂度O(logn)
 * <p>
 * 相比于二分查找(折半),该算法考虑的是每次折的时候折多少,即不一定是1/2;如在一本字典中找"abstract"这个单词,
 * 我们自己来操作肯定是先翻到字典开始的那一小部分,而不是从字典的中间开始进行折半查找<br>
 * 在二分查找中mid=(low+high)/2=low+1/2*(high-low),插值查找就是对1/2(系数,或者说因子)进行改变<br>
 * 将1/2变成 (key - array[low])/(array[high] - array[low]),其实就是计算线性比例<br>
 * 那么计算线性比例就有一个问题,如果当前数组分布不是均匀的,那么该算法就不合适
 * 
 * @author Tianma
 *
 */
public class InterpolateSearcher implements Searcher {

	@Override
	public int search(int[] array, int key) {
		int low, high, mid;
		low = 0; // 定义最低下标为array首位
		high = array.length - 1; // 定义最高下标为array末位
		while (low <= high) {
			// 相比二分法查找的更改处
			mid = low + (int) (1.0 * (key - array[low]) / (array[high] - array[low]) * (high - low));
			if (array[mid] > key) {
				// 中值比key大,则high=mid-1
				high = mid - 1;
			} else if (array[mid] < key) {
				// 中值比key小,则low=mid+1
				low = mid + 1;
			} else {
				// 相等说明mid即为key在array中所在位置
				return mid;
			}
		}
		return -1;
	}

}

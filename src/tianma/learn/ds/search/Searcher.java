package tianma.learn.ds.search;

public interface Searcher {

	/**
	 * 从数组array中查找关键字key,如果存在则返回该关键字在数组中任意出现的位置(不局限于首次或者末次之类的),否则返回-1
	 * 
	 * @param array
	 * @param key
	 * @return
	 */
	int search(int[] array, int key);

}

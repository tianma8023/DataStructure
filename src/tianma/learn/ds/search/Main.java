package tianma.learn.ds.search;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		int[] array = { 1, 16, 24, 35, 47, 59, 62, 73, 88, 99 };
		System.out.println("array : " + Arrays.toString(array));
		int key = 62;
		System.out.println("key : " + key);
		// 线性查找
		LinearSearcher ls = new LinearSearcher();
		System.out.println("linear search: " + ls.search(array, key));
		// 优化线性查找
		OptimizedLinearSearcher ols = new OptimizedLinearSearcher();
		System.out.println("optimized linear search: " + ols.search(array, key));
		// 二分查找
		BinarySearcher bs = new BinarySearcher();
		System.out.println("binary search: " + bs.search(array, key));
		// 插值查找
		InterpolateSearcher is = new InterpolateSearcher();
		System.out.println("interpolate search: " + is.search(array, key));
		// 斐波那契查找
		FibonacciSearcher fs = new FibonacciSearcher();
		System.out.println("fibonacci search: " + fs.search(array, key));

	}

}

package tianma.learn.ds.sort;

import java.util.Random;

public class SorterMain {

	public static void main(String[] args) {
		int[] arr = { 9, 1, 5, 8, 3, 7, 4, 6, 2 };
		SorterTester tester = new SorterTester();
		tester.addSorter(new BubbleSorter(), "BubbleSort");
		tester.addSorter(new OptimizedBubbleSorter(), "Optimized BubbleSort");
		tester.addSorter(new SelectionSorter(), "SelectionSort");
		tester.addSorter(new InsertSorter(), "InsertSort");
		tester.addSorter(new ShellSorter(), "ShellSort");
		tester.addSorter(new HeapSorter(), "HeapSort");
		tester.addSorter(new MergeSorter(), "MergeSort");
		tester.addSorter(new NonRecursiveMergeSorter(), "NonRecursiveMergeSort");
		tester.addSorter(new QuickSorter(), "QuickSort");
		tester.addSorter(new OptimizedQuickSorter(), "OptimizedQuickSort");
		tester.print(arr);
		int[] newArr = new int[1000];
		Random rand = new Random();
		int len = newArr.length;
		for (int i = 0; i < len; i++) {
			newArr[i] = rand.nextInt(len);
		}
		tester.print(newArr);
	}

}

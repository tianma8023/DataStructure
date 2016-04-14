package tianma.learn.ds.sort;

public class SorterMain {

	public static void main(String[] args) {
		int[] arr = { 9, 1, 5, 8, 3, 7, 4, 6, 2 };
		SorterTester tester = new SorterTester();
		tester.addSorter(new BubbleSorter(), "BubbleSort");
		tester.addSorter(new OptimizedBubbleSorter(), "Optimized BubbleSort");
		tester.addSorter(new SelectionSorter(), "SelectionSort");
		tester.print(arr);
	}

}

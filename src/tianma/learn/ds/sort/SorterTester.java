package tianma.learn.ds.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SorterTester {

	private List<Sorter> sorters;
	private List<String> descrips;

	public SorterTester() {
		sorters = new ArrayList<Sorter>();
		descrips = new ArrayList<String>();
	}

	public SorterTester addSorter(Sorter sorter, String desc) {
		if (sorter == null)
			return this;
		sorters.add(sorter);
		if (desc == null)
			desc = "";
		descrips.add(desc);
		return this;
	}

	public void print(int[] arr) {
		System.out.println("Source Array : \n" + Arrays.toString(arr));
		for (int i = 0; i < sorters.size(); i++) {
			int len = arr.length;
			int[] tmp = new int[len];
			System.arraycopy(arr, 0, tmp, 0, len);
			Sorter sorter = sorters.get(i);
			String descrip = descrips.get(i);
			System.out.println(descrip + " :");
			tmp = sorter.sort(tmp);
			System.out.println(Arrays.toString(tmp));
		}

	}

}

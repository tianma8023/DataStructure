package tianma.learn.ds.lineartable;

import tianma.learn.ds.lineartable.MyDoublyList;

public class DoublyLinkListTest {

	public static void main(String[] args) {
		MyDoublyList<Character> list = new MyDoublyList<Character>();
		list.add('a');
		System.out.println(list);
		System.out.println(list.removeAt(0));
		System.out.println(list.remove(0));
	}

}

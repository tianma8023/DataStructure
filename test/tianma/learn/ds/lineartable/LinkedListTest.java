package tianma.learn.ds.lineartable;

import tianma.learn.ds.lineartable.MyLinkedList;
import tianma.learn.ds.lineartable.MyList;

public class LinkedListTest extends AbstractListTest {

	@Override
	protected MyList<Character> newInstance() {
		return new MyLinkedList<Character>();
	}

}

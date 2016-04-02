package tianma.learn.ds.list;

public class LinkedListTest extends AbstractListTest {

	@Override
	protected MyList<Character> newInstance() {
		return new MyLinkedList<Character>();
	}

}

package tianma.learn.ds.lineartable;

public class LinkedStackTest extends AbstractStackTest {

	@Override
	protected MyStack<Character> newInstance() {
		return new MyLinkedStack<Character>();
	}

}

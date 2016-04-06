package tianma.learn.ds.lineartable;

public class LinkedQueueTest extends AbstractQueueTest {

	@Override
	protected MyQueue<Character> newInstance() {
		return new MyLinkedQueue<Character>();
	}

}

package tianma.learn.ds.lineartable;

public class ArrayCircleQueueTest extends AbstractQueueTest {

	@Override
	protected MyQueue<Character> newInstance() {
		return new MyArrayCircleQueue<Character>();
	}

}

package tianma.learn.ds.lineartable;

public class ArrayStackTest extends AbstractStackTest {

	@Override
	protected MyStack<Character> newInstance() {
		return new MyArrayStack<Character>();
	}

}

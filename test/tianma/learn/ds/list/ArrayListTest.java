package tianma.learn.ds.list;

public class ArrayListTest extends AbstractListTest{

	@Override
	protected MyList<Character> newInstance() {
		return new MyArrayList<Character>();
	}

}

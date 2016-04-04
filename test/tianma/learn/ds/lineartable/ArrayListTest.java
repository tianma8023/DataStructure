package tianma.learn.ds.lineartable;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyList;

public class ArrayListTest extends AbstractListTest{

	@Override
	protected MyList<Character> newInstance() {
		return new MyArrayList<Character>();
	}

}

package tianma.learn.ds.list;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

abstract public class AbstractListTest {

	
	private MyList<Character> list;

	@Before
	public void prepare() {
		list = newInstance();
		for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
			list.add(c);
		}
//		System.out.println(list);
	}

	abstract protected MyList<Character> newInstance();

	@Test
	public void testIterator() {
		for (Character c : list) {
			System.out.print(c + " ");
		}
		System.out.println();
		for (Iterator<Character> ite = list.iterator(); ite.hasNext();) {
			System.out.print(ite.next() + " ");
		}
	}

	@Test
	public void testRemove() {
		Assert.assertSame('a', list.removeAt(0));
		Assert.assertEquals(25, list.size());
		Assert.assertTrue(list.remove('g'));
		Assert.assertEquals(24, list.size());
	}

	@Test
	public void testClear() {
		Assert.assertFalse(list.isEmpty());
		list.clear();
		Assert.assertTrue(list.isEmpty());
	}
	
	@Test
	public void testIndexOf() {
		Assert.assertEquals(1, list.indexOf('b'));
		Assert.assertEquals(-1, list.indexOf("Hello"));
		Assert.assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	public void testSetGet() {
		Assert.assertSame('b', list.get(1));
		Assert.assertSame('b', list.set(1, 'e'));
		Assert.assertSame('e', list.get(1));
	}

	
}

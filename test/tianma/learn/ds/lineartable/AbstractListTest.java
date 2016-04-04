package tianma.learn.ds.lineartable;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tianma.learn.ds.lineartable.MyList;

abstract public class AbstractListTest {

	private MyList<Character> list;

	@Before
	public void prepare() {
		list = newInstance();
		for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
			list.add(c);
		}
		System.out.println(list);
	}

	abstract protected MyList<Character> newInstance();

	@Test
	public void testIterator() {
		System.out.println("Test Iterator");
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
		System.out.println("testing remove started");
		Assert.assertSame('a', list.removeAt(0));
		Assert.assertEquals(25, list.size());
//		Assert.assertTrue(list.remove('g'));
		System.out.println(list.remove('g'));
		Assert.assertEquals(24, list.size());
		System.out.println("testing remove finished");
	}

	@Test
	public void testClear() {
		System.out.println("testing clear");
		Assert.assertFalse(list.isEmpty());
		list.clear();
		Assert.assertTrue(list.isEmpty());
	}

	@Test
	public void testIndexOf() {
		System.out.println("testing indexOf");
		Assert.assertEquals(1, list.indexOf('b'));
		Assert.assertEquals(-1, list.indexOf("Hello"));
		Assert.assertEquals(-1, list.indexOf(null));
	}

	@Test
	public void testSetGet() {
		System.out.println("testing get");
		Assert.assertSame('b', list.get(1));
		Assert.assertSame('b', list.set(1, 'e'));
		Assert.assertSame('e', list.get(1));
	}

}

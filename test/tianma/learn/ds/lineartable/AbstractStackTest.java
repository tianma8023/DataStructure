package tianma.learn.ds.lineartable;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

abstract public class AbstractStackTest {

	private MyStack<Character> stack;

	@Before
	public void prepare() {
		System.out.print("preparing  ");
		stack = newInstance();
		for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
			stack.push(c);
		}
		System.out.println(stack);
	}

	abstract protected MyStack<Character> newInstance();

	@Test
	public void testIterator() {
		System.out.println("testing iterator started");
		for (Character c : stack) {
			System.out.print(c + " ");
		}
		System.out.println();
		for (Iterator<Character> ite = stack.iterator(); ite.hasNext();) {
			System.out.print(ite.next() + " ");
		}
		System.out.println();
		System.out.println("testing iterator finished");
	}

	@Test
	public void testPop() {
		System.out.println("testing pop started");
		Assert.assertSame('z', stack.pop());
		Assert.assertEquals(25, stack.size());
		System.out.println("testing pop finished");
	}

	@Test
	public void testClear() {
		System.out.println("testing clear started");
		Assert.assertFalse(stack.isEmpty());
		stack.clear();
		Assert.assertTrue(stack.isEmpty());
		System.out.println("testing clear finished");
	}

	@Test
	public void testPeek() {
		System.out.println("testing peek started");
		Assert.assertSame('z', stack.peek());
		Assert.assertEquals(26, stack.size());
		System.out.println("testing peek finished");
	}

}

package tianma.learn.ds.lineartable;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

abstract public class AbstractQueueTest {

	private MyQueue<Character> queue;

	@Before
	public void prepare() {
		System.out.print("preparing  ");
		queue = newInstance();
		for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
			queue.offer(c);
		}
		System.out.println(queue);
		// queue = newInstance();
		// queue.offer('a');
		// queue.offer('b');
		// queue.offer('c');
		// queue.offer('d');
		// System.out.println(queue);
		// System.out.println(queue.remove());
		// System.out.println(queue);
		// queue.offer('e');
		// System.out.println(queue);
		// queue.offer('f');
		// System.out.println(queue);
	}

	abstract protected MyQueue<Character> newInstance();

	@Test
	public void testIterator() {
		System.out.println("testing iterator started");
		for (Character c : queue) {
			System.out.print(c + " ");
		}
		System.out.println();
		for (Iterator<Character> ite = queue.iterator(); ite.hasNext();) {
			System.out.print(ite.next() + " ");
		}
		System.out.println();
		while (!queue.isEmpty()) {
			System.out.print(queue.remove() + " ");
		}
		System.out.println();
		System.out.println("testing iterator finished");
	}

	@Test
	public void testRemove() {
		System.out.println("testing remove started");
		Assert.assertSame('a', queue.remove());
		Assert.assertEquals(25, queue.size());
		System.out.println("testing remove finished");
	}

	@Test
	public void testClear() {
		System.out.println("testing clear started");
		Assert.assertFalse(queue.isEmpty());
		queue.clear();
		Assert.assertTrue(queue.isEmpty());
		System.out.println("testing clear finished");
	}

	@Test
	public void testElement() {
		System.out.println("testing element() started");
		Assert.assertSame('a', queue.element());
		Assert.assertEquals(26, queue.size());
		System.out.println("testing element() finished");
	}

}

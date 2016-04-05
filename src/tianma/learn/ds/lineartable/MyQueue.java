package tianma.learn.ds.lineartable;

import java.util.NoSuchElementException;

public interface MyQueue<E> extends Iterable<E> {

	/**
	 * Remove all the elements from this queue.
	 */
	void clear();

	/**
	 * Retrieves, but does not remove, the head of this queue. It throws an
	 * exception if this queue is empty.
	 * 
	 * @return
	 */
	E element();

	/**
	 * Whether the queue is empty or not.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Inserts the specified element into this queue if it is possible to do so
	 * immediately without violating capacity restrictions, returning
	 * {@code true} upon success and throwing an {@code IllegalStateException}
	 * if no space is currently available.
	 * 
	 * @return
	 */
	boolean offer(E e);

	/**
	 * Retrieves and removes the head of this queue. It throws an exception if
	 * this queue is empty.
	 *
	 * @return the head of this queue
	 * @throws NoSuchElementException
	 *             if this queue is empty
	 */
	E remove();

	/**
	 * Retrieves the size of this queue
	 * 
	 */
	int size();

}

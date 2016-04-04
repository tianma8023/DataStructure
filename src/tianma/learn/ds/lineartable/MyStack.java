package tianma.learn.ds.lineartable;

public interface MyStack<E> extends Iterable<E> {

	void clear();

	boolean isEmpty();

	/**
	 * Pushes an item onto the top of this stack.
	 * 
	 * @param e
	 *            the element to be pushed into this stack
	 * @return true if push succeed, false if not.
	 */
	boolean push(E e);

	/**
	 * Gets the element at the top of this stack without removing it.
	 * 
	 * @return
	 */
	E peek();

	/**
	 * Removes the element at the top of this stack and returns that element as
	 * the value of this function.
	 * 
	 * @return
	 */
	E pop();

	/**
	 * Gets the size of this stack
	 * 
	 * @return
	 */
	int size();

}

package tianma.learn.ds.lineartable;

/**
 * 
 * @author Tianma
 *
 */
public interface MyList<E> extends Iterable<E> {

	/**
	 * Appends the specified element to the end of list.
	 * 
	 * @param e
	 * @return
	 */
	boolean add(E e);

	/**
	 * Inserts the specified element at the specified position in this list.
	 * 
	 * @param index
	 * @param e
	 * @return
	 */
	boolean add(int index, E e);

	/**
	 * Remove all of the elements from this list.
	 * 
	 * @return
	 */
	void clear();

	/**
	 * Gets the element at specific index
	 * 
	 * @param index
	 * @return
	 */
	E get(int index);

	/**
	 * Returns the index of the first occurrence of the specified element in the
	 * list, or -1 if this list doesn't contain the element.
	 * 
	 * @param o
	 * @return
	 */
	int indexOf(Object o);

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Removes the first occurrence of the specified element from this list.
	 * 
	 * @param o
	 * @return
	 */
	boolean remove(Object o);

	/**
	 * Removes the elements at the specified position from this list.
	 * 
	 * @param index
	 * @return the element previously at the specified position
	 */
	E removeAt(int index);

	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 * 
	 * @param index
	 * @param e
	 * @return the element previously at the specified position.
	 */
	E set(int index, E e);

	/**
	 * The size of list
	 * 
	 * @return
	 */
	int size();

}

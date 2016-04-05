package tianma.learn.ds.lineartable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Tianma
 */
public class MyArrayList<E> implements MyList<E> {

	private Object[] elementData;

	private int size;
	// private int capacity;

	private static final int DEFAULT_CAPACITY = 5;

	public MyArrayList(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity :" + initialCapacity);
		}
		int capacity = Math.max(DEFAULT_CAPACITY, initialCapacity);
		elementData = new Object[capacity];
	}

	public MyArrayList() {
		elementData = new Object[DEFAULT_CAPACITY];
	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<E> {

		int cursor; // index of next element to return

		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		@Override
		public E next() {
			if (cursor >= size)
				throw new NoSuchElementException();
			E retValue = elementData(cursor);
			cursor++;
			return retValue;
		}

	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity - elementData.length > 0) {
			grow(minCapacity);
		}
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// + 优先级大于 >>
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		newCapacity = Math.max(newCapacity, minCapacity);
		if (newCapacity > MAX_ARRAY_SIZE) {
			if (minCapacity < 0)
				throw new OutOfMemoryError();
			newCapacity = minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	@Override
	public boolean add(E e) {
		ensureCapacity(size + 1);
		elementData[size] = e;
		size++;
		return true;
	}

	@Override
	public boolean add(int index, E e) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		ensureCapacity(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = e;
		size++;
		return true;
	}

	private String outOfBoundsMsg(int index) {
		return "index: " + index + ", size = " + size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++)
			elementData[i] = null;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	private E elementData(int index) {
		return (E) elementData[index];
	}

	@Override
	public E get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		return elementData(index);
	}

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null)
					return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (o.equals(elementData[i]))
					return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index == -1)
			return false;
		removeAt(index);
		return true;
	}

	@Override
	public E removeAt(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		E oldValue = elementData(index);
		int numMove = size - index - 1;
		if (numMove > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMove);
		elementData[size - 1] = null;
		size--;
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E set(int index, E e) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		E oldValue = elementData(index);
		elementData[index] = e;
		return oldValue;
	}

	@Override
	public String toString() {
		Iterator<E> it = iterator();
		if (!it.hasNext())
			return "[]";
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			E e = it.next();
			sb.append(e == this ? "(this Collection)" : e);
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(',').append(' ');
		}
	}

}

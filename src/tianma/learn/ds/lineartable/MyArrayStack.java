package tianma.learn.ds.lineartable;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

public class MyArrayStack<E> implements MyStack<E> {

	private static final int DEFAULT_CAPACITY = 5;
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private Object[] elementData;
	private int top = -1; // 栈顶游标

	public MyArrayStack() {
		elementData = new Object[DEFAULT_CAPACITY];
	}

	public MyArrayStack(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal capacity :" + initialCapacity);
		}
		int capacity = Math.max(DEFAULT_CAPACITY, initialCapacity);
		elementData = new Object[capacity];
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {

		private int cursor;

		public Itr() {
			cursor = top;
		}

		@Override
		public boolean hasNext() {
			return cursor > -1;
		}

		@Override
		public E next() {
			E retValue = elementData(cursor);
			cursor--;
			return retValue;
		}

	}

	@Override
	public void clear() {
		for (int i = 0; i < top + 1; i++)
			elementData[i] = null;
		top = -1;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean push(E e) {
		ensureCapacity(size() + 1);
		top++;
		elementData[top] = e;
		return true;
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity - elementData.length > 0) {
			grow(minCapacity);
		}
	}

	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		newCapacity = Math.max(minCapacity, oldCapacity);
		if (newCapacity > MAX_ARRAY_SIZE) {
			if (minCapacity < 0)
				throw new OutOfMemoryError();
			newCapacity = minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	@SuppressWarnings("unchecked")
	private E elementData(int index) {
		return (E) elementData[index];
	}

	@Override
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return elementData(top);
	}

	@Override
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();
		E value = elementData(top);
		elementData[top] = null;
		top--;
		return value;
	}

	@Override
	public int size() {
		return top + 1;
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

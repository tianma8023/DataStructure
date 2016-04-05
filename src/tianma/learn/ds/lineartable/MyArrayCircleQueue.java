package tianma.learn.ds.lineartable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 顺序存储式的循环队列(预留一个空闲单元)
 * 
 * @author Tianma
 *
 */
public class MyArrayCircleQueue<E> implements MyQueue<E> {

	private static final int DEFAULT_CAPACITY = 5;
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private Object[] elementData;
	private int front; // 队首元素index
	private int rear; // 队尾元素的下一个位置

	public MyArrayCircleQueue() {
		this(DEFAULT_CAPACITY);
	}

	public MyArrayCircleQueue(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal capacity : " + initialCapacity);
		int capacity = Math.max(initialCapacity, DEFAULT_CAPACITY);
		elementData = new Object[capacity];
		front = rear = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<E> {

		private int cursor;
		private int counter;

		public Iter() {
			cursor = front;
			counter = 0;
		}

		@Override
		public boolean hasNext() {
			return counter < size();
		}

		@Override
		public E next() {
			E value = elementData(cursor);
			cursor = (cursor + 1) % elementData.length;
			counter++;
			return value;
		}

	}

	@Override
	public void clear() {
		// 方案一,分段考虑
		// if (front < rear) {
		// // 分成一段, [front, rear - 1]
		// for (int i = front; i < rear; i++) {
		// elementData[i] = null;
		// }
		// } else if (front == rear) {
		// // isEmpty() = true
		// // do nothing
		// } else {
		// // 分为两段,[0, rear-1] [front, elementData.length - 1]
		// for (int i = 0; i < rear; i++) {
		// elementData[i] = null;
		// }
		// for (int i = front; i < elementData.length; i++) {
		// elementData[i] = null;
		// }
		// }
		// 方案二,亦或是这样
		for (int i = 0; i < size(); i++) {
			elementData[(i + front) % elementData.length] = null;
		}
		front = rear = 0;
	}

	@SuppressWarnings("unchecked")
	private E elementData(int index) {
		return (E) elementData[index];
	}

	@Override
	public E element() {
		if (isEmpty())
			throw new NoSuchElementException();
		return elementData(front);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean offer(E e) {
		ensureCapacity(size() + 1);
		elementData[rear] = e;
		rear = (rear + 1) % elementData.length;
		return true;
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity >= elementData.length) {
			grow(minCapacity);
		}
	}

	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// + 优先级大于 >>
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		newCapacity = Math.max(minCapacity, newCapacity);
		if (newCapacity > MAX_ARRAY_SIZE) {
			if (minCapacity < 0)
				throw new OutOfMemoryError();
			newCapacity = minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
		}

		Object[] newArray = new Object[newCapacity];
		if (front < rear) {
			// Array中的元素是连续一整块的
			System.arraycopy(elementData, front, newArray, 0, rear - front);
		} else {
			// Array中的元素是左边和右边各一部分 [0, rear -1], [front, elementData.length-1]
			int rightLen = oldCapacity - front;
			// int leftLen = oldCapacity - rightLen - 1;
			int leftLen = rear;
			System.arraycopy(elementData, front, newArray, 0, rightLen);
			System.arraycopy(elementData, 0, newArray, rightLen, leftLen);
		}
		elementData = newArray;
		front = 0;
		rear = oldCapacity - 1;
	}

	@Override
	public E remove() {
		if (isEmpty())
			throw new NoSuchElementException();
		E value = elementData(front);
		elementData[front] = null;
		front = (front + 1) % elementData.length;
		return value;
	}

	@Override
	public int size() {
		return (rear - front + elementData.length) % elementData.length;
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

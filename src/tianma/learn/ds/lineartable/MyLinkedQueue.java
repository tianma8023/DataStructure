package tianma.learn.ds.lineartable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedQueue<E> implements MyQueue<E> {

	private Node<E> front;
	private Node<E> rear;
	private int size;

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
	}

	public MyLinkedQueue() {
		front = rear = new Node<E>(null, null);
	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<E> {

		private Node<E> curNode;

		public Iter() {
			curNode = front.next;
		}

		@Override
		public boolean hasNext() {
			return curNode != null;
		}

		@Override
		public E next() {
			E value = curNode.element;
			curNode = curNode.next;
			return value;
		}

	}

	@Override
	public void clear() {
		Node<E> tmp = front;
		while (tmp.next != null) {
			Node<E> target = tmp.next;
			tmp.next = target.next;
			target.next = null;
			target.element = null;
			target = null;
		}
		size = 0;
	}

	@Override
	public E element() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return front.next.element;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean offer(E e) {
		// 队尾元素入队
		Node<E> tmp = new Node<E>(e, null);
		rear.next = tmp;
		rear = tmp;
		size++;
		return true;
	}

	@Override
	public E remove() {
		// 队首元素出队
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<E> tmp = front.next;
		front.next = front.next.next;
		if (tmp == rear) {
			// 如果对头是队尾,也就是当前队列只剩下一个元素
			rear = front;
		}
		E value = tmp.element;
		tmp.next = null;
		tmp = null;
		size--;
		return value;
	}

	@Override
	public int size() {
		return size;
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

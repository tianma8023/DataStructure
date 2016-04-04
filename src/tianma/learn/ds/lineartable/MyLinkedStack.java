package tianma.learn.ds.lineartable;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 链式栈(不含头结点)
 * 
 * @author Tianma
 *
 */
public class MyLinkedStack<E> implements MyStack<E> {

	private Node<E> top;
	private int size;

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

	}

	public MyLinkedStack() {
		top = null;
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {

		private Node<E> curNode;

		public Itr() {
			curNode = top;
		}

		@Override
		public boolean hasNext() {
			return curNode != null;
		}

		@Override
		public E next() {
			if (curNode == null)
				throw new NoSuchElementException();
			E value = curNode.element;
			curNode = curNode.next;
			return value;
		}

	}

	@Override
	public void clear() {
		while (!isEmpty()) {
			Node<E> tmp = top;
			top = top.next;
			tmp.next = null;
			tmp.element = null;
			tmp = null;
			size--;
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean push(E e) {
		Node<E> tmp = new Node<E>(e, null);
		tmp.next = top;
		top = tmp;
		size++;
		return true;
	}

	@Override
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return top.element;
	}

	@Override
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();
		Node<E> tmp = top;
		E value = tmp.element;
		top = top.next;
		tmp.next = null;
		tmp.element = null;
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

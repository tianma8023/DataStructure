package tianma.learn.ds.lineartable;

import java.util.Iterator;

/**
 * 单链表(带头结点)
 * 
 * @author Tianma
 *
 */
public class MyLinkedList<E> implements MyList<E> {

	private transient Node<E> head;
	private transient Node<E> tail;

	private int size;

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
	}

	public MyLinkedList() {
		head = new Node<E>(null, null);
		tail = head;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIter();
	}

	private class ListIter implements Iterator<E> {

		private Node<E> curNode;

		public ListIter() {
			curNode = head;
		}

		@Override
		public boolean hasNext() {
			return curNode.next != null;
		}

		@Override
		public E next() {
			E retValue = curNode.next.element;
			curNode = curNode.next;
			return retValue;
		}

	}

	@Override
	public boolean add(E e) {
		Node<E> tmp = new Node<E>(e, null);
		tail.next = tmp;
		tail = tmp;
		size++;
		return true;
	}

	private String outOfBoundsMsg(int index) {
		return "index: " + index + ", size = " + size;
	}

	@Override
	public boolean add(int index, E e) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		if (index == size) {
			return add(e);
		} else {
			Node<E> target = new Node<E>(e, null);
			Node<E> tmp = head;
			for (int i = 0; i < index; i++) {
				tmp = tmp.next;
			}
			target.next = tmp.next;
			tmp.next = target;
			size++;
			return true;
		}
	}

	@Override
	public void clear() {
		Node<E> tmp = head;
		while (tmp.next != null) {
			Node<E> target = tmp.next;
			tmp.next = tmp.next.next;
			target.next = null;
			target.element = null;
			target = null;
		}
		size = 0;
	}

	@Override
	public E get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		Node<E> tmp = head.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		return tmp.element;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (tmp.element == null)
					return index;
				tmp = tmp.next;
				index++;
			}
		} else {
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (o.equals(tmp.element))
					return index;
				tmp = tmp.next;
				index++;
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
		if (o == null) {
			Node<E> tmp = head;
			while (tmp.next != null) {
				if (tmp.next.element == null) {
					Node<E> target = tmp.next;
					tmp.next = tmp.next.next;
					target.next = null;
					target.element = null;
					target = null;
					size--;
					return true;
				}
				tmp = tmp.next;
			}
		} else {
			Node<E> tmp = head;
			while (tmp.next != null) {
				if (o.equals(tmp.next.element)) {
					Node<E> target = tmp.next;
					tmp.next = tmp.next.next;
					target.next = null;
					target.element = null;
					target = null;
					size--;
					return true;
				}
				tmp = tmp.next;
			}
		}
		return false;
	}

	@Override
	public E removeAt(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		Node<E> tmp = head;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		Node<E> target = tmp.next;
		tmp.next = tmp.next.next;
		target.next = null;
		E retValue = target.element;
		target.element = null;
		target = null;
		size--;
		return retValue;
	}

	@Override
	public E set(int index, E e) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		Node<E> tmp = head.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		E retValue = tmp.element;
		tmp.element = e;
		return retValue;
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

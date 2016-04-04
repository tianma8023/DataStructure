package tianma.learn.ds.lineartable;

import java.util.Iterator;

/**
 * 双向链表(含头结点,尾节点)
 * 
 * @author Tianma
 */
public class MyDoublyList<E> implements MyList<E> {

	private transient Node<E> head; // the header node of list
	private transient Node<E> tail; // the last node of list
	private int size;

	private static class Node<E> {
		Node<E> priv;
		E element;
		Node<E> next;

		public Node(Node<E> priv, E element, Node<E> next) {
			this.priv = priv;
			this.element = element;
			this.next = next;
		}
	}

	public MyDoublyList() {
		head = new Node<E>(null, null, null);
		tail = new Node<E>(null, null, null);
		head.next = tail;
		tail.priv = head;
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<E> {

		private Node<E> curNode;

		public Iter() {
			curNode = head.next;
		}

		@Override
		public boolean hasNext() {
			return curNode != tail;
		}

		@Override
		public E next() {
			E element = curNode.element;
			curNode = curNode.next;
			return element;
		}

	}

	@Override
	public boolean add(E e) {
		Node<E> tmp = new Node<E>(null, e, null);
		tmp.next = tail;
		tmp.priv = tail.priv;
		tail.priv.next = tmp;
		tail.priv = tmp;
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
			Node<E> target = new Node<E>(null, e, null);
			if (index <= (size >> 1)) {
				// 如果index在前半段,则从head节点开始遍历
				Node<E> tmp = head;
				for (int i = 0; i < index; i++) {
					tmp = tmp.next;
				}
				target.next = tmp.next;
				target.priv = tmp.next.priv;
				tmp.next.priv = target;
				tmp.next = target;
			} else {
				// 如果index在后半段,则从tail节点开始遍历
				Node<E> tmp = tail;
				for (int i = 0; i < size - index; i++) {
					tmp = tmp.priv;
				}
				target.next = tmp;
				target.priv = tmp.priv;
				tmp.priv.next = target;
				tmp.priv = target;
			}
			size++;
			return true;
		}
	}

	@Override
	public void clear() {
		Node<E> tmp = head.next;
		while (tmp != null) {
			tmp.next.priv = tmp.priv;
			tmp.priv.next = tmp.next;
			tmp.next = null;
			tmp.priv = null;
			tmp = null;
		}
		size = 0;
	}

	@Override
	public E get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		E target;
		if (index <= (size >> 1)) {
			// 如果index在前半段,则从head节点开始遍历
			Node<E> tmp = head.next;
			for (int i = 0; i < index; i++) {
				tmp = tmp.next;
			}
			target = tmp.element;
		} else {
			// 如果index在后半段,则从tail节点开始遍历
			Node<E> tmp = tail;
			for (int i = 0; i < size - index; i++) {
				tmp = tail.priv;
			}
			target = tmp.element;
		}
		return target;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (tmp.element == null)
					return index;
				index++;
				tmp = tmp.next;
			}
		} else {
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (o.equals(tmp.element))
					return index;
				index++;
				tmp = tmp.next;
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
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (tmp.element == null) {
					tmp.priv.next = tmp.next;
					tmp.next.priv = tmp.priv;
					tmp.next = null;
					tmp.priv = null;
					tmp = null;
					size--;
					return true;
				}
				tmp = tmp.next;
			}
		} else {
			Node<E> tmp = head.next;
			while (tmp != null) {
				if (o.equals(tmp.element)) {
					tmp.priv.next = tmp.next;
					tmp.next.priv = tmp.priv;
					tmp.next = null;
					tmp.priv = null;
					tmp = null;
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
		Node<E> tmp = head.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		tmp.priv.next = tmp.next;
		tmp.next.priv = tmp.priv;
		E oldValue = tmp.element;
		tmp.next = null;
		tmp.priv = null;
		tmp = null;
		size--;
		return oldValue;
	}

	@Override
	public E set(int index, E e) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		Node<E> tmp = head.next;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		E oldValue = tmp.element;
		tmp.element = e;
		return oldValue;
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

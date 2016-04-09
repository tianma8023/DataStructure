package tianma.learn.ds.tree;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyList;

/**
 * 二叉排序树(又称:二叉搜索树):<br>
 * 定义：<br>
 * 1. 如果它的左子树不为空，那么左子树的所有节点都小于根节点的值<br>
 * 2. 如果它的右子树不为空，那么右子树的所有节点都大于根节点的值<br>
 * 3. 它的左、右子树也分别是二叉排序树<br>
 * 二叉排序树不稳定，其查找性能取决于其树的形状。最差时间复杂度为O(n)，即左斜树或者右斜树
 * 
 * @author Tianma
 *
 */
public class SortedBinaryTree<E> implements Tree<E> {

	private Node<E> root; // 根节点

	private int size; // 二叉树元素个数

	/**
	 * 二叉树节点
	 */
	private static class Node<E> {
		E element; // 节点元素
		Node<E> lChild; // 左孩子
		Node<E> rChild; // 右孩子

		public Node(E element) {
			this(element, null, null);
		}

		public Node(E element, Node<E> lChild, Node<E> rChild) {
			this.element = element;
			this.lChild = lChild;
			this.rChild = rChild;
		}
	}

	public SortedBinaryTree(MyList<E> elements) {
		for (E e : elements) {
			add(e);
		}
	}

	public SortedBinaryTree(E[] elements) {
		for (E e : elements) {
			add(e);
		}
	}

	public SortedBinaryTree() {
	}

	/**
	 * 判断当前元素是否存在于树中
	 * 
	 * @param element
	 * @return
	 */
	public boolean contains(E element) {
		return search(root, element);
	}

	/**
	 * 递归搜索,查找当前以curRoot为根节点的树中element存在与否
	 * 
	 * @param curRoot
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean search(Node<E> curRoot, E element) {
		if (curRoot == null)
			return false;
		Comparable<? super E> e = (Comparable<? super E>) element;
		int cmp = e.compareTo(curRoot.element);
		if (cmp > 0) {
			// 查找的元素大于当前根节点对应的元素,向右走
			return search(curRoot.rChild, element);
		} else if (cmp < 0) {
			// 查找的元素小于当前根节点对应的元素,向左走
			return search(curRoot.lChild, element);
		} else {
			// 查找的元素等于当前根节点对应的元素,返回true
			return true;
		}
	}

	/**
	 * 非递归搜索,查找当前以curRoot为根节点的树中的element是否存在
	 * 
	 * @param curRoot
	 *            二叉排序树的根节点
	 * @param element
	 *            被搜索的元素
	 * @param target
	 *            target[0]指向查找路径上最后一个节点: 如果当前查找的元素存在,则target[0]指向该节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean find(Node<E> curRoot, E element, Node<E>[] target) {
		if (curRoot == null)
			return false;
		Node<E> tmp = curRoot;
		Comparable<? super E> e = (Comparable<? super E>) element;
		while (tmp != null) {
			int cmp = e.compareTo(tmp.element);
			target[0] = tmp;
			if (cmp > 0) {
				// 查找的元素大于当前节点对应的元素,向右走
				tmp = tmp.rChild;
			} else if (cmp < 0) {
				// 查找的元素小于当前节点对应的元素,向左走
				tmp = tmp.lChild;
			} else {
				// 查找的元素等于当前根节点对应的元素,返回true
				return true;
			}
		}
		return false;
	}

	/**
	 * 向二叉排序树中添加元素,如果当前元素已经存在,则添加失败,返回false,如果当前元素不存在,则添加成功,返回true
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean add(E element) {
		if (root == null) {
			root = new Node<E>(element);
			size++;
			return true;
		}
		Node<E>[] target = new Node[1];
		if (!find(root, element, target)) {
			// 当前元素不存在,插入元素
			// 此时target节点即为需要插入的节点的父节点
			Comparable<? super E> e = (Comparable<? super E>) element;
			int cmp = e.compareTo(target[0].element);
			Node<E> newNode = new Node<E>(element);
			if (cmp > 0) {
				// 插入的元素大于target指向的节点元素
				target[0].rChild = newNode;
			} else {
				// 插入的元素小于target指向的节点元素
				target[0].lChild = newNode;
			}
			size++;
			return true;
		}
		return false;
	}

	/**
	 * 删除二叉排序树中的元素,如果当前元素不存在,则删除失败,返回false;如果当前元素存在,则删除该元素,重构二叉树,返回true
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(E element) {
		Node<E>[] target = new Node[1];
		if (find(root, element, target)) {
			// 被删除的元素存在,则继续执行删除操作
			remove(target[0]);
			return true;
		}
		return false;
	}

	/**
	 * 释放当前节点
	 * 
	 * @param node
	 */
	private void free(Node<E> node) {
		node.element = null;
		node.lChild = null;
		node.rChild = null;
		node = null;
	}

	/**
	 * 删除二叉排序树中指定的节点
	 * 
	 * @param node
	 */
	private void remove(Node<E> node) {
		Node<E> tmp;
		if (node.lChild == null && node.rChild == null) {
			// 当前node为叶子节点,删除当前节点,则node = null;
			node = null;
		} else if (node.lChild == null && node.rChild != null) {
			// 如果被删除的节点左子树为空,则只需要重新连接其右子树
			tmp = node;
			node = node.rChild;
			free(tmp);
		} else if (node.lChild != null && node.rChild == null) {
			// 如果被删除的节点右子树为空,则只需要重新连接其左子树
			tmp = node;
			node = node.lChild;
			free(tmp);
		} else {
			// 当前被删除的节点左右子树均存在,不为空
			// 找到离当前node节点对应元素且最近的节点target(左子树的最右边节点 或者 右子树最左边节点)
			// 将node节点元素替换成target节点的元素,将target节点删除
			tmp = node; // tmp是target的父节点
			Node<E> target = node.lChild; // 找到左子树最大子树
			while (target.rChild != null) { // 在左子树中进行右拐
				tmp = target;
				target = target.rChild;
			}
			node.element = target.element; // node.element元素替换为target.element
			if (tmp == node) {
				// tmp == node 说明没有在左子树中进行右拐,也就是node节点的左孩子没有右孩子,
				// 需要重新连接tmp节点左孩子
				tmp.lChild = target.lChild;
			} else {
				// tmp != node, 进行了右拐,那么将重新连接tmp的右子树,将target.lChild赋值给tmp.rChild
				tmp.rChild = target.lChild;
			}
			// 释放节点
			free(target);
		}
		// 删除成功,size--;
		size--;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public MyList<E> preOrderTraverse() {
		MyList<E> list = new MyArrayList<E>();
		preOrderTraverse(root, list);
		return list;
	}

	private void checkList(MyList<E> list) {
		if (list == null)
			throw new NullPointerException("MyList cannot be null");
	}

	private void preOrderTraverse(Node<E> curRoot, MyList<E> list) {
		checkList(list);
		if (curRoot == null)
			return;
		E e = curRoot.element;
		list.add(e);
		preOrderTraverse(curRoot.lChild, list);
		preOrderTraverse(curRoot.rChild, list);
	}

	@Override
	public MyList<E> inOrderTraverse() {
		MyList<E> list = new MyArrayList<E>();
		inOrderTraverse(root, list);
		return list;
	}

	private void inOrderTraverse(Node<E> curRoot, MyList<E> list) {
		checkList(list);
		if (curRoot == null)
			return;
		inOrderTraverse(curRoot.lChild, list);
		list.add(curRoot.element);
		inOrderTraverse(curRoot.rChild, list);
	}

	@Override
	public MyList<E> postOrderTraverse() {
		MyList<E> list = new MyArrayList<E>();
		postOrderTraverse(root, list);
		return list;
	}

	private void postOrderTraverse(Node<E> curRoot, MyList<E> list) {
		checkList(list);
		if (curRoot == null)
			return;
		inOrderTraverse(curRoot.lChild, list);
		inOrderTraverse(curRoot.rChild, list);
		list.add(curRoot.element);
	}

	/**
	 * 返回中序遍历结果
	 */
	@Override
	public String toString() {
		return inOrderTraverse().toString();
	}

	public static void main(String[] args) {
		Integer[] elements = { 62, 88, 58, 47, 35, 73, 51, 99, 37, 93 };
		elements = new Integer[] { 62, 88, 58, 47, 73, 99, 35, 51, 93, 29, 37, 49, 56, 36, 48, 50 };
		SortedBinaryTree<Integer> tree = new SortedBinaryTree<Integer>(elements);
		System.out.println(tree);
		System.out.println(tree.contains(93));
		System.out.println(tree.size());
		System.out.println(tree.remove(47));
		System.out.println(tree.preOrderTraverse());
		System.out.println(tree.size());
	}

}

package tianma.learn.ds.tree;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyList;

/**
 * AVL树(平衡二叉树)，是一种自平衡的二叉搜索树
 * <p>
 * 它是一种特殊的二叉搜索树，其每个节点的左子树和右子树的高度差最多等于1
 * 
 * @author Tianma
 *
 */
public class BalancedBinaryTree<E> implements Tree<E>{

	/**
	 * 树的根节点
	 */
	private Node<E> root;

	/**
	 * 树中的元素个数
	 */
	private int size;

	/**
	 * 树中的节点
	 *
	 * @param <E>
	 */
	private static class Node<E> {

		/**
		 * 平衡因子-右高-值为-1
		 */
		public static final int BF_RH = -1;
		/**
		 * 平衡因子-等高-值为0
		 */
		public static final int BF_EH = 0;
		/**
		 * 平衡因子-左高-值为1
		 */
		public static final int BF_LH = 1;

		E element; // 节点代表的元素
		int bf; // 平衡因子,值为BF_RH或BF_EH或BF_LH
		Node<E> lChild; // 左孩子
		Node<E> rChild; // 右孩子
		Node<E> parent; // 父节点

		public Node(E element) {
			this(element, null);
		}
		
		public Node(E element, Node<E> parent) {
			this(element, BF_EH, null, null, parent);
		}

		public Node(E element, int bf, Node<E> lChild, Node<E> rChild, Node<E> parent) {
			this.element = element;
			this.bf = bf;
			this.lChild = lChild;
			this.rChild = rChild;
			this.parent = parent;
		}

		@Override
		public String toString() {
			return "Node [element=" + element + ", bf=" + bf + "]";
		}
		
	}

	public BalancedBinaryTree() {

	}

	@SuppressWarnings("unchecked")
	public BalancedBinaryTree(Object[] elements) {
		for(Object e : elements) {
			add((E)e);
		}
	}

	public BalancedBinaryTree(MyList<E> elements) {
		for(E e : elements) {
			add(e);
		}
	}

	/**
	 * 对以curNode为根节点的二叉排序树进行右旋处理
	 * 比如：
     * 	    A(1)       插入N,平                  A(2)      A失衡,       B(0)
     *     /   \        衡破坏		 /    \     右旋                        /     \
     *    B(0)  AR(0)  ---->    B(1) AR(0)  ---->   BL(1)  A(0)
     *   /   \                 /   \                 |    /    \
     *  BL(0)  BR(0)         BL(1) BR(0)            N(0) BR(0) AR(0)
     *                         |
     *                         N 
     *   
	 * @param curNode
	 *            当前需要进行右旋的子二叉树根节点
	 */
	private void rightRotate(Node<E> curNode) {
		Node<E> left = curNode.lChild; // left节点指向curNode的左孩子节点
		curNode.lChild = left.rChild; // curNode左孩子指向left的右孩子
		left.rChild = curNode; // left的右孩子指向curNode,即curNode变成left的右孩子
		curNode = left; // curNode指向left，即curNode指向新的根节点
	}

	/**
	 * 对以curNode为根节点的二叉排序树进行左旋处理
     * 比如：
     *     A(1)       插入N,平                   A(2)      A失衡,        B(0)
     *   /    \        衡破坏		/    \     左旋                            /     \
     *  AL(0) B(0)    ---->   AL(0) B(1)   ---->   A(0)     BR(1)  
     *       /   \                 /   \           /  \      |     
     *     BL(0)BR(0)            BL(0) BR(1)    AL(0) BL(0)  N(0)     
     *                                   |
     *                                   N(0)  
	 * @param curNode
	 *            当前需要进行左旋的子二叉树根节点
	 */
	private void leftRotate(Node<E> curNode) {
		Node<E> right = curNode.rChild; // right节点指向curNode的右孩子节点
		curNode.rChild = right.lChild; // curNode的右孩子指向right的左孩子
		right.lChild = curNode; // right的左孩子指向curNode,即curNode变成right的左孩子
		curNode = right; // curNode指向right,即curNode指向新的根节点
	}
	
	/**
	 * 对当前t对应的子树进行左平衡处理，算法结束时，t指向新的根节点
	 * 左平衡有以下可能性：
	 * 情况0,新插入点在t的左孩子的左子树上:
	 *       t(1)                t(2)                    l(0)
	 *      /  \     插入节点N     /    \     单右旋处理                      /    \
	 *    l(0)  r(0) ----->   l(1)    r(0) ----->    ll(1)   t(0)   
	 *   /   \               /  \                     |     /    \
	 *  ll(0) lr(0)       ll(1) lr(0)                N(0)  lr(0) r(0) 
	 *                      |
	 *                      N(0)
	 * 平衡因子变化的有 : t.bf = l.bf = 0;
	 * **********************************************************************
	 * 情况1：
	 *        t(1)               t(2)            t(2)             lr(0)
	 *       /   \    插入节点N     /   \    左旋l   /   \     右旋t    /    \
	 *     l(0)  r(0) ----->   l(-1) r(0) -->  lr(2) r(0) --->  l(0)  t(-1)
	 *    /   \                /  \           /                /  \      \ 
	 *  ll(0) lr(0)         ll(0) lr(1)      l(0)           l1(0) N(0)   r(0)
	 *                             /        /   \              
	 *                           N(0)      ll(0) N(0)   
	 * 平衡因子变化的有： t.bf = -1; l.bf = 0; lr.bf = 0;
	 * *********************************************************************** 
	 * 情况2：
	 *        t(1)               t(2)            t(2)                lr(0)
	 *       /   \    插入节点N     /   \    左旋l   /   \     右旋t      /      \
	 *     l(-1) r(0) ----->   l(-1) r(0) -->  lr(1) r(0) --->  l(0)       t(0)
	 *    /   \                /  \           /  \             /   \        /  \ 
	 *  ll(0) lr(1)         ll(0) lr(0)      l(0) N(0)       ll(0) lrl(0) N(0) r(0)
	 *        /                   /   \     /   \                
	 *     lrl(0)              lrl(0) N(0) ll(0) lrl(0)   
	 * 平衡因子变化的有： t.bf = 0; l.bf = 0; lr.bf = 0;
	 * ***********************************************************************                           
	 */
	private void leftBalance(Node<E> t) {
		Node<E> l = t.lChild; // l为t的左孩子
		switch (l.bf) {
		// 检查t左子树的平衡度，并作相应平衡处理
		case Node.BF_LH:
			// 情况0：
			// 新插入点在t的左孩子的左子树上，要作单右旋处理
			t.bf = l.bf = Node.BF_EH;
			rightRotate(t);
			break;
		case Node.BF_RH:
			// 新插入的节点在t的左孩子的右子树上，需要作双旋处理
			Node<E> lr = l.rChild; // lr指向T的左孩子的右孩子
			switch (lr.bf) {
			case Node.BF_LH:
				// 情况1
				l.bf = Node.BF_EH;
				t.bf = Node.BF_RH;
				break;

			case Node.BF_EH:
				// 情况2
				t.bf = l.bf = Node.BF_EH;
				break;
				
			case Node.BF_RH:
				t.bf = Node.BF_EH;
				l.bf = Node.BF_LH;

			}
			lr.bf = Node.BF_EH;
			leftRotate(t.lChild);
			rightRotate(t);
		}
	}
	/**
	 * 对当前t对应的子树进行左平衡处理，算法结束时，t指向新的根节点
	 */
	private void rightBalance(Node<E> t){  
        Node<E> r = t.rChild;  
        switch (r.bf) {  
        case Node.BF_LH:            //左高，分情况调整  
            Node<E> ld = r.lChild;  
            switch (ld.bf) {   //调整各个节点的BF  
            case Node.BF_LH:    //情况1  
                t.bf = Node.BF_EH;  
                r.bf = Node.BF_RH;  
                break;  
            case Node.BF_EH:    //情况2  
                t.bf = r.bf = Node.BF_EH;  
                break;  
            case Node.BF_RH:    //情况3  
                t.bf = Node.BF_LH;  
                r.bf = Node.BF_EH;  
                break;  
            }  
            ld.bf = Node.BF_EH;  
            rightRotate(t.rChild);  
            leftRotate(t);  
            break;  
        case Node.BF_RH:            //右高，左旋调整  
            t.bf = r.bf = Node.BF_EH;  
            leftRotate(t);  
            break;  
//        case Node.BF_EH:       //特殊情况4  
//            r.bf = Node.BF_LH;  
//            t.bf = Node.BF_RH;  
//            leftRotate(t);  
//            heightLower = false;  
//            break;  
        }  
    } 
	
	public void add(E element) {
		if(insert(root, element)) {
			size++;
		}
	}
	
	private boolean taller = false;
	
	@SuppressWarnings("unchecked")
	private boolean insert(Node<E> curNode,E element) {
		if(curNode == null) {
			curNode = new Node<E>(element);
			taller = true;
			if(root == null){
				root = curNode;
			}
			return true;
		}
		Comparable<? super E> e = (Comparable<? super E>) element;
		int cmp = e.compareTo(curNode.element);
		if (cmp == 0) {
			// 树中节点element已经存在,不再插入
			taller = false;
			return false;
		}
		if (cmp < 0) {
			// element小于当前节点对应元素，向左搜索
			if(!insert(curNode.lChild, element)) {
				// 如果插入左子树失败,则返回false
				return false;
			}
			if(taller) {
				// 插入左子树成功,且左子树"长高"
				switch (curNode.bf) { // 检查以当前节点为根节点的二叉树的平衡度
				case Node.BF_LH:
					// 原本该左子树就比右子树高，现在左子树再"长高",则需要进行左平衡处理
					leftBalance(curNode);
					// 处理完毕，当前左子树于右子树登高，taller置为false
					taller = false; 
					break;
				case Node.BF_EH:
					// 原本该左子树和右子树登高，现在该左子树"长高"，所以左子树比右子树高
					curNode.bf = Node.BF_LH;
					taller = true;
					break;
				case Node.BF_RH:
					// 原本该左子树比右子树低，现在左子树"长高",则左右等高
					curNode.bf = Node.BF_EH;
					taller = false;
				}
			}
		} else if (cmp > 0) {
			// element大于当前节点对应元素,向右搜索
			if(insert(curNode.rChild, element)) {
				// 插入右子树失败,返回false
				return false;
			}
			if(taller) {
				// 插入右子树成功,且右子树"长高"
				switch (curNode.bf) { // 检查以当前节点为根节点的二叉树的平衡度
				case Node.BF_LH:
					// 原本左子树比右子树高,现在右子树"长高",则左右登高
					curNode.bf = Node.BF_EH;
					taller = false;
					break;
				case Node.BF_EH:
					// 原本左子树和右子树登高，现在右子树"长高",则右子树比左子树高
					curNode.bf = Node.BF_RH;
					taller = true;
					break;
				case Node.BF_RH:
					// 原本左子树比右子树低，现在右子树"长高"，则需要进行右平衡处理
					rightBalance(curNode);
					// 处理完毕，当前左子树于右子树登高，taller置为false
					taller = false;
				}
			}
		}
		return true;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
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
		elements = new Integer[] {3, 2, 1, 4, 5, 6, 7, 10, 9, 8};
		BalancedBinaryTree<Integer> tree = new BalancedBinaryTree<Integer>(elements);
		System.out.println(tree.root);
		System.out.println(tree);
		System.out.println(tree.size());
		System.out.println(tree.preOrderTraverse());
	}
	
}

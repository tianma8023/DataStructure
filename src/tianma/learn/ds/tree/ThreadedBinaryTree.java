package tianma.learn.ds.tree;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyList;

/**
 * 线索二叉树(详情见大话数据结构第二季第6章6.10小节)
 * <p>
 * 参考资料<br>
 * <a href="http://128kj.iteye.com/blog/1634367">二叉树的线索化Java</a><br>
 * <a href="http://blog.chinaunix.net/uid-26548237-id-3476920.html">数据结构之线索二叉树</a>
 * 这里实现中序线索二叉树
 * @author Tianma
 *
 */
public class ThreadedBinaryTree implements Tree<Character> {

	/**
	 * 二叉树根节点
	 */
	private TreeNode<Character> root;

	/**
	 * 中序线索化时所保存的前驱节点
	 */
	private TreeNode<Character> preNode;
	
	/**
	 * 是否已经中序线索化
	 */
	private boolean hasInThreaded = false; 

	/**
	 * 线索二叉树节点
	 * 
	 * @author Tianma
	 *
	 */
	public static class TreeNode<E> {
		/**
		 * lTag = LINK表示lChild指针指向左孩子; rTag = LINK表示rChild指针指向右孩子
		 */
		public static final int LINK = 0;
		/**
		 * lTag = THREAD表示lChild指向前驱节点; rTag = THREAD表示rChild指针指向后驱节点
		 */
		public static final int THREAD = 1;

		E element;
		TreeNode<E> lChild; // 左子节点
		int lTag; // 左标志
		TreeNode<E> rChild; // 右子节点
		int rTag; // 右标志

		/**
		 * 默认节点的lTag = LINK, rTag = LINK
		 * @param element
		 */
		public TreeNode(E element) {
			this(element, null, LINK, null, LINK);
		}

		/**
		 * 默认节点的lTag = LINK, rTag = LINK
		 * @param element
		 * @param lChild
		 * @param rChild
		 */
		public TreeNode(E element, TreeNode<E> lChild, TreeNode<E> rChild) {
			this(element, lChild, LINK, rChild, LINK);
		}

		public TreeNode(E element, TreeNode<E> lChild, int lTag, TreeNode<E> rChild, int rTag) {
			this.element = element;
			this.lChild = lChild;
			this.lTag = lTag;
			this.rChild = rChild;
			this.rTag = rTag;
		}

		@Override
		public String toString() {
			return "TreeNode [element=" + element + ", lChild=" + lChild + ",dlTag=" + lTag + ", rChild=" + rChild
					+ ", rTag=" + rTag + "]";
		}
	}

	/**
	 * 以initTreeStr构建二叉树,默认不进行线索化
	 * @param initTreeStr 必须符合完全二叉树的格式(子树不存在则用#补充) 
	 */
	public ThreadedBinaryTree(String initTreeStr) {
		this(initTreeStr, false);
	}
	
	/**
	 * 
	 * @param initTreeStr
	 * @param threading 是否进行线索化
	 */
	public ThreadedBinaryTree(String initTreeStr, boolean threading) {
		// 构建二叉树
		buildTree(initTreeStr);
		if(threading) {
			inThread(root);
			// 已经进行过中序线索化
			hasInThreaded = true;
		}
	}
	
	/**
	 * 以完全二叉树的格式来创建(子树不存在的用#填充)<br>
	 * 如  ****A*******<br>
	 *  **B**********D*****<br>
	 * *#***C******E**<br>
	 * 用字符串表示为 abd#c <br>
	 * 如果对完全二叉树节点按层进行从0开始编号,那么编号为i的节点的左孩子为2*i+1, 右孩子为2*i+2
	 * @param initTreeStr
	 */
	//       A
	//    |——————|
	//    B      D
	// |————|  |———
	// #    C  E
	private void buildTree(String initTreeStr) {
		int len = initTreeStr.length();
		MyList<TreeNode<Character>> nodeList = new MyArrayList<TreeNode<Character>>();
		// 将strTree中的元素转换为TreeNode节点,并存放于List中
		for (int i = 0; i < len; i++) {
			char ch = initTreeStr.charAt(i);
			TreeNode<Character> node;
			if (ch == '#') {
				node = null;
			} else {
				node = new TreeNode<Character>(ch);
			}
			nodeList.add(node);
		}
		// 完全二叉树来讲, 子节点为1的非叶节点数n1的值要么是0要么是1
		// 该二叉树的最后一个非叶节点x的下标 = n/2 - 1,其中n为总的节点数
		// 我们先考虑x节点之前的节点,这些节点都是度为2的节点：
		for (int parentIdx = 0; parentIdx < len / 2 - 1; parentIdx++) {
			TreeNode<Character> node = nodeList.get(parentIdx);
			// 左孩子
			node.lChild = nodeList.get(parentIdx * 2 + 1);
			// 右孩子
			node.rChild = nodeList.get(parentIdx * 2 + 2);
		}
		// 最后一个非叶节点的节点x要么度(子节点个数)为1,要么度为2。当n为偶数时,x度为1,x只有左节点;当n为奇数时,x度为2个
		int index = len / 2 - 1; // 唯一一个子节点为1的节点位置
		nodeList.get(index).lChild = nodeList.get(index * 2 + 1);
		if (len % 2 == 1) {
			// n为奇数,x节点有右节点
			nodeList.get(index).rChild = nodeList.get(index * 2 + 2);
		}
		if(!nodeList.isEmpty()){
			root = nodeList.get(0);
		}
	}
	
	/**
	 * 将当前二叉树以root为根节点进行二叉树线索化,使之成为中序线索二叉树
	 * @param curNode 当前节点
	 * @param preNode 当前节点的前驱节点
	 */
	private void inThread(TreeNode<Character> curNode) {
		if (curNode == null)
			return;
		// 递归左子树,将左子树线索化
		inThread(curNode.lChild);
		if (curNode.lChild == null) {
			// 如果当前节点左孩子为null,则将当前节点的左孩子设置为线索,指向当前节点的前驱节点
			curNode.lTag = TreeNode.THREAD;
			curNode.lChild = preNode;
		} 
		if (preNode != null && preNode.rChild == null) {
			// 如果当前节点的前驱节点不为null,且该前驱节点的右子节点为null,则将前驱节点的右孩子设置为线索,并指向当前节点
			preNode.rTag = TreeNode.THREAD;
			preNode.rChild = curNode;
		}
		// preNode指向当前节点的前驱节点
		preNode = curNode;
		// 递归右子树,将右子树线索化
		inThread(curNode.rChild);
	}
	
	
	private void checkList(MyList<Character> list) {
		if (list == null)
			throw new NullPointerException("MyList cannot be null");
	}
	
	@Override
	public MyList<Character> preOrderTraverse() {
		MyList<Character> preList = new MyArrayList<Character>();
		preOrderTraverse(root, preList);
		return preList;
	}
	

	private void preOrderTraverse(TreeNode<Character> curNode, MyList<Character> list) {
		checkList(list);
		if (curNode == null)
			return;
		Character ch = curNode.element;
		list.add(ch);
		preOrderTraverse(curNode.lChild, list);
		preOrderTraverse(curNode.rChild, list);
	}

	@Override
	public MyList<Character> inOrderTraverse() {
		if (hasInThreaded) {
			return inOrderThreadTraverse();
		} else {
			MyList<Character> inList = new MyArrayList<Character>();
			inOrderTraverse(root, inList);
			return inList;
		}
	}
	
	private void inOrderTraverse(TreeNode<Character> curNode, MyList<Character> list) {
		checkList(list);
		if (curNode == null)
			return ;
		inOrderTraverse(curNode.lChild, list);
		Character ch = curNode.element;
		list.add(ch);
		inOrderTraverse(curNode.rChild, list);
	}
	
	/**
	 * 线索化的二叉树的中序遍历
	 * @param root
	 */
	private MyList<Character> inOrderThreadTraverse() {
		MyList<Character> list = new MyArrayList<Character>();
		TreeNode<Character> tmp = root;
		while(tmp != null && tmp.lTag == TreeNode.LINK) {
			// 当左孩子不是线索节点时,继续遍历,直至左孩子是线索节点为止
			tmp = tmp.lChild;
		}
		while(tmp != null) {
			list.add(tmp.element);
			if (tmp.rTag == TreeNode.THREAD) {
				// 如果当前节点的rChild为线索,则直接遍历右孩子
				tmp = tmp.rChild;
			}else {
				// 如果当前节点的rChild不为线索,即当前节点右孩子存在
				// 则遍历当前节点的右子树的左子节点,直至找到线索节点为止
				tmp = tmp.rChild;
				while(tmp != null && tmp.lTag == TreeNode.LINK) {
					tmp = tmp.lChild;
				}
			}
		}
		return list;
	}

	@Override
	public MyList<Character> postOrderTraverse() {
		MyList<Character> postList = new MyArrayList<Character>();
		postOrderTraverse(root, postList);
		return postList;
	}
	
	private void postOrderTraverse(TreeNode<Character> curNode, MyList<Character> list) {
		checkList(list);
		if (curNode == null)
			return ;
		postOrderTraverse(curNode.lChild, list);
		postOrderTraverse(curNode.rChild, list);
		Character ch = curNode.element;
		list.add(ch);
	}
	
	public static void main(String[] args) {
		String initTreeStr = "ABD#CE";
		ThreadedBinaryTree tree = new ThreadedBinaryTree(initTreeStr); // 普通的二叉树(未经线索化)
		System.out.println("普通二叉树:");
		System.out.println("preOrder:  " + tree.preOrderTraverse());
		System.out.println("inOrder:   " + tree.inOrderTraverse());
		System.out.println("postOrder: " + tree.postOrderTraverse());
		
		ThreadedBinaryTree tree2 = new ThreadedBinaryTree(initTreeStr, true); // 经过线索化的二叉树
		System.out.println("中序线索化二叉树:");
		System.out.println("inOrder:   " + tree2.inOrderTraverse());
	}

}

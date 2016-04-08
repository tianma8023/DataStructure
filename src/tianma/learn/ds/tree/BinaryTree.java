package tianma.learn.ds.tree;

import tianma.learn.ds.lineartable.MyArrayList;
import tianma.learn.ds.lineartable.MyList;

/**
 * 一般二叉树,包含二叉树构造,二叉树先序遍历,中序遍历,后序遍历
 * 
 * @author Tianma
 *
 */
public class BinaryTree implements Tree<Character> {

	private TreeNode<Character> root; // 二叉树根节点

	private static class TreeNode<E> {
		E element; // the element of tree node.
		TreeNode<E> lChild; // left child
		TreeNode<E> rChild; // right child

		public TreeNode(E element) {
			this(element, null, null);
		}

		public TreeNode(E element, TreeNode<E> lChild, TreeNode<E> rChild) {
			this.element = element;
			this.lChild = lChild;
			this.rChild = rChild;
		}

		@Override
		public String toString() {
			return "TreeNode [element=" + element + ", lChild=" + lChild + ", rChild=" + rChild + "]";
		}
	}
	
	/**
	 * 以initTreeStr构建二叉树
	 * @param initTreeStr 必须符合完全二叉树的格式(子树不存在则用#补充) 
	 */
	public BinaryTree(String initTreeStr) {
		buildTree(initTreeStr);
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
		MyList<Character> inList = new MyArrayList<Character>();
		inOrderTraverse(root, inList);
		return inList;
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
		BinaryTree tree = new BinaryTree(initTreeStr);
		System.out.println("preOrder:  " + tree.preOrderTraverse());
		System.out.println("inOrder:   " + tree.inOrderTraverse());
		System.out.println("postOrder: " + tree.postOrderTraverse());
	}

}

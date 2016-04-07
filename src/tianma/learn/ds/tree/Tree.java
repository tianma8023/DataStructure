package tianma.learn.ds.tree;

import tianma.learn.ds.lineartable.MyList;

public interface Tree<E> {

	/**
	 * PreOrder traverse(先序遍历: 根节点 -> 左子树 -> 右子树)
	 * 
	 * @return the list of element by PreOrder traverse
	 */
	MyList<E> preOrderTraverse();

	/**
	 * InOrder traverse(中序遍历: 左子树 -> 根节点 -> 右子树)
	 * 
	 * @return the list of element by InOrder traverse
	 */
	MyList<E> inOrderTraverse();

	/**
	 * PostOrder traverse(后序遍历: 左子树 -> 右子树 -> 根节点)
	 * 
	 * @return the list of element by PostOrder traverse
	 */
	MyList<E> postOrderTraverse();

}

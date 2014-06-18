package leamon.datastruct.tree;

/**
 * 二叉排序树
 */
public class BSTTree implements Tree {

	private BSTNode root;// 根节点

	public TreeNode getRoot() {
		return this.root;
	}

	public BSTNode findMax(BSTNode root) {
		if (root == null) {
			return null;
		}

		while (root.right != null) {
			root = root.right;
		}

		return root;
	}

	public BSTNode findMin(BSTNode root) {
		if (root == null) {
			return null;
		}

		if (root.left == null) {
			return root;
		} else {
			return findMin(root.left);
		}

	}

	public void inside(int data) {
		root = inside(root, data);
	}

	private BSTNode inside(BSTNode root, int data) {
		if (root == null) {
			root = new BSTNode(data);
		} else if (data < root.data) {
			root.left = inside(root.left, data);
		} else {
			root.right = inside(root.right, data);
		}

		return root;
	}

	public void delete(int data) {
		root = delete(root, data);
	}

	private BSTNode delete(BSTNode root, int data) {
		if (root == null) {
			return null;
		}

		if (data < root.data) {
			root.left = delete(root.left, data);
		} else if (data > root.data) {
			root.right = delete(root.right, data);
		} else {// data == root.data
			if (root.left != null && root.right != null) {
				root.data = findMin(root.right).data;
				root.right = delete(root.right, root.data);
			} else {
				root = root.left != null ? root.left : root.right;
			}
		}
		return root;
	}

	public boolean contain(int data) {
		return contain(root, data);
	}

	private boolean contain(BSTNode root, int data) {
		if (root == null) {
			return false;
		}

		if (data < root.data) {
			return contain(root.left, data);
		} else if (data > root.data) {
			return contain(root.right, data);
		} else {
			return true;
		}

	}

	public void print() {
		print(root);
	}

	private void print(BSTNode root) {
		if (root != null) {
			print(root.left);
			System.out.println(root.data);
			print(root.right);
		}
	}

	public TreeNode search(int data) {
		return search(root, data);
	}

	private BSTNode search(BSTNode root, int data) {
		if (root == null) {
			return null;
		}

		if (data < root.data) {
			return search(root.left, data);
		} else if (data > root.data) {
			return search(root.right, data);
		} else {// data == root.data
			return root;
		}
	}

	@Override
	public Tree getLeftTree() {
		if (root == null) {
			return null;
		}

		BSTTree tree = new BSTTree();
		tree.root = root.left;

		return tree;
	}

	@Override
	public Tree getRightTree() {
		if (root == null) {
			return null;
		}

		BSTTree tree = new BSTTree();
		tree.root = root.right;

		return tree;
	}

	/**
	 * 二叉树节点结构描述
	 */
	public static class BSTNode implements Tree.TreeNode {
		private int data;// 数据域
		private BSTNode left;// 左子树根节点
		private BSTNode right;// 右子树根节点

		public BSTNode(int data) {
			this.data = data;
			this.left = this.right = null;
		}

		@Override
		public TreeNode getLeft() {
			return this.left;
		}

		@Override
		public TreeNode getRight() {
			return this.right;
		}
	}
}

package leamon.datastruct.tree;

/**
 * 平衡二叉树
 */
public class AVLTree implements Tree {

    private AVLNode root;// AVLTree 根节点

    public void inside(int data) {
        root = inside(root, data);
    }

    private AVLNode inside(AVLNode root, int data) {
        if (root == null) {
            return new AVLNode(data);
        }

        if (data < root.data) {
            root.left = inside(root.left, data);

            if (height(root.left) - height(root.right) == 2) {
                if (data < root.left.data) {
                    root = rotationWithLeftChild(root);
                } else {
                    root = doubleWithLeftChild(root);
                }
            }
        } else if (data > root.data) {
            root.right = inside(root.right, data);

            if (height(root.right) - height(root.left) == 2) {
                if (data > root.right.data) {
                    root = rotationWithRightChild(root);
                } else {
                    root = doubleWithRightChild(root);
                }
            }
        } else {
            // do noting;
        }

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return root;
    }

    public void delete(int data) {
        root = delete(root, data);
    }

    private AVLNode delete(AVLNode root, int data) {
        if (root == null) {
            return null;
        }

        if (data < root.data) {
            root.left = delete(root.left, data);
        } else if (data > root.data) {
            root.right = delete(root.right, data);
        } else {
            if (root.left != null && root.right != null) {
                root.data = findMin(root.right).data;
                root.right = delete(root.right, root.data);
            } else {
                root = root.left != null ? root.left : root.right;
            }
        }

        if (root != null) {
            if (height(root.left) - height(root.right) == 2) {
                if (root.left.right != null) {// LR-Rotation
                    root = doubleWithLeftChild(root);
                } else {// LL-Rotation
                    root = rotationWithLeftChild(root);
                }
            } else if (height(root.right) - height(root.left) == 2) {
                if (root.right.left != null) {
                    root = doubleWithRightChild(root);
                } else {
                    root = rotationWithRightChild(root);
                }
            } else {
                // do noting!
            }
            root.height = Math.max(height(root.left), height(root.right)) + 1;
        }

        return root;
    }

    public void print() {
        print(root);
    }

    private void print(AVLNode root) {
        if (root != null) {
            print(root.left);
            System.out.println(root.data);
            print(root.right);
        }
    }

    public boolean contain(int data) {
        return contain(root, data);
    }

    private boolean contain(AVLNode root, int data) {
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

    @Override
    public TreeNode search(int data) {
        return search(root, data);
    }

    private AVLNode search(AVLNode root, int data) {
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
    public TreeNode getRoot() {
        return this.root;
    }

    public AVLNode findMax(AVLNode root) {
        if (root == null) {
            return null;
        }

        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    public AVLNode findMin(AVLNode root) {
        if (root == null) {
            return null;
        }

        if (root.left != null) {
            return findMin(root.left);
        } else {
            return root;
        }
    }

    /**
     * 左左旋转 LL-Rotation
     * 
     * @param root
     * @return
     */
    private AVLNode rotationWithLeftChild(AVLNode root) {
        AVLNode node = root.left;
        root.left = node.right;
        node.right = root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        node.height = Math.max(height(node.left), root.height) + 1;
        return node;
    }

    /**
     * 右右旋转 RR-Rotation
     * 
     * @param root
     * @return
     */
    private AVLNode rotationWithRightChild(AVLNode root) {
        AVLNode node = root.right;
        root.right = node.left;
        node.left = root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        node.height = Math.max(root.height, height(node.right)) + 1;
        return node;
    }

    /**
     * 左右旋转 LR-Rotation
     * 
     * @param root
     * @return
     */
    private AVLNode doubleWithLeftChild(AVLNode root) {
        root.left = rotationWithRightChild(root.left);
        return rotationWithLeftChild(root);
    }

    /**
     * 右左旋转 RL-Rotation
     * 
     * @param root
     * @return
     */
    private AVLNode doubleWithRightChild(AVLNode root) {
        root.right = rotationWithLeftChild(root.right);
        return rotationWithRightChild(root);
    }

    /**
     * AVLNode的高度
     * 
     * @param node
     * @return
     */
    protected int height(AVLNode node) {
        return node == null ? -1 : node.height;
    }

    @Override
    public Tree getLeftTree() {
        if (root == null) {
            return null;
        }

        AVLTree tree = new AVLTree();
        tree.root = root.left;

        return tree;
    }

    @Override
    public Tree getRightTree() {
        if (root == null) {
            return null;
        }

        AVLTree tree = new AVLTree();
        tree.root = root.right;

        return tree;
    }

    /**
     * AVL树的节点描述
     */
    public static class AVLNode implements Tree.TreeNode {
        private int data;// 数据域
        private AVLNode left;// 左子树根节点
        private AVLNode right;// 右子树跟节点
        private int height;// 子树的高度

        public AVLNode(int data) {
            this.data = data;
            this.left = this.right = null;
            this.height = 0;
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

package leamon.datastruct.heap;

/**
 * 左式堆，本质上是一颗二叉树
 */
public class LeftistHeap {

    private LeftistNode root;// 根节点，保证该二叉树具有堆序性
    private int size;

    public LeftistHeap() {
        this.root = null;
        this.size = 0;
    }

    public void insert(int value) {
        LeftistNode node = new LeftistNode(value, null, null);
        root = merge(node, root);
        size++;
    }

    public int deleteMin() {
        int result = findMin();
        root = merge(root.left, root.right);
        size--;
        return result;
    }

    public int findMin() {
        if (isEmpty()) {
            String message = "current heap is empty!";
            throw new RuntimeException(message);
        }
        return root.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * 合并操作
     * 
     * @param x
     * @param y
     * @return
     */
    private LeftistNode merge(LeftistNode x, LeftistNode y) {
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }

        // 比较两颗树的根，保证根节点小的右子树与另外的树合并作为其新的右子树
        if (x.value > y.value) {
            LeftistNode t = x;
            x = y;
            y = t;
        }

        x.right = merge(x.right, y);

        if (x.left == null || x.left.npl < x.right.npl) {
            LeftistNode t = x.left;
            x.left = x.right;
            x.right = t;
        }

        if (x.right == null || x.left == null) {
            x.npl = 0;
        } else {
            x.npl = (x.left.npl > x.right.npl) ? (x.right.npl + 1) : (x.left.npl + 1);
        }

        return x;
    }

    /**
     * 堆节点
     */
    private static class LeftistNode {
        private int value;
        private int npl;// null path length
        private LeftistNode left;
        private LeftistNode right;

        public LeftistNode(int value, LeftistNode left, LeftistNode right) {
            this.value = value;
            this.npl = 0;
            this.left = left;
            this.right = right;
        }
    }

}

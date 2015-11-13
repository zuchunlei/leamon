package leamon.datastruct.tree;

/**
 * Hash树
 */
public class HashTree<T, E> {

    private static final int[] LEVEL = { 2, 5, 7, 11, 13, 17, 19, 23, 29, 31 };// 连续质数

    private TreeNode<T, E> root;// 根节点

    public HashTree() {
        root = new TreeNode<T, E>(0);
    }

    /**
     * 添加操作
     * 
     * @param key
     * @param value
     */
    public void put(T key, E value) {
        interPut(root, key, value);
    }

    /**
     * 根据Key查找
     * 
     * @param key
     * @return
     */
    public E get(T key) {
        TreeNode<T, E> node = interGet(root, key);
        return node == null ? null : node.entry.value;
    }

    /**
     * 判断Key是否存在
     * 
     * @param key
     * @return
     */
    public boolean containsKey(T key) {
        return interGet(root, key) != null;
    }

    /**
     * 根据Key删除
     * 
     * @param key
     * @return
     */
    public E remove(T key) {
        TreeNode<T, E> node = interGet(root, key);
        if (node == null) {
            return null;
        }
        E value = node.entry.value;
        node.occupy = false;
        node.entry.key = null;
        node.entry.value = null;
        return value;
    }

    private TreeNode<T, E> interPut(TreeNode<T, E> node, T key, E value) {
        if (!node.occupy) {// 节点没有被占用，使用
            node.occupy = true;
            node.entry.key = key;
            node.entry.value = value;
            return node;
        }

        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % LEVEL[node.level]);

        TreeNode<T, E> succeedNode = node.points[index];
        if (succeedNode == null) {
            succeedNode = new TreeNode<T, E>(node.level + 1);
            node.points[index] = succeedNode;
        }

        return interPut(succeedNode, key, value);
    }

    private TreeNode<T, E> interGet(TreeNode<T, E> node, T key) {
        if (node == null) {
            return null;
        }

        if (node.occupy && node.entry.key.equals(key)) {// 节点被占用
            return node;
        }

        int index = Math.abs(key.hashCode() % LEVEL[node.level]);
        return interGet(node.points[index], key);
    }

    static class TreeNode<M, N> {
        private boolean occupy;// 是否被占用
        private int level;// 节点所处层树
        private Entry<M, N> entry;// 节点
        private TreeNode<M, N>[] points;// 后继节点指针

        @SuppressWarnings("unchecked")
        private TreeNode(int level) {
            this.level = level;
            this.points = new TreeNode[LEVEL[level]];
            this.entry = new Entry<M, N>();
        }
    }

    static class Entry<K, V> {
        K key;
        V value;
    }

    public static void main(String[] args) {
        HashTree<String, Integer> tree = new HashTree<>();
        for (int i = 0; i < 10000; i++) {
            tree.put(String.valueOf(i), i);
        }
    }
}

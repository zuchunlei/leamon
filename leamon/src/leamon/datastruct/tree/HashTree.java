package leamon.datastruct.tree;

/**
 * Hash��
 */
public class HashTree<T, E> {

    private static final int[] LEVEL = { 2, 5, 7, 11, 13, 17, 19, 23, 29, 31 };// ��������

    private TreeNode<T, E> root;// ���ڵ�

    public HashTree() {
        root = new TreeNode<T, E>(0);
    }

    /**
     * ��Ӳ���
     * 
     * @param key
     * @param value
     */
    public void put(T key, E value) {
        interPut(root, key, value);
    }

    /**
     * ����Key����
     * 
     * @param key
     * @return
     */
    public E get(T key) {
        TreeNode<T, E> node = interGet(root, key);
        return node == null ? null : node.entry.value;
    }

    /**
     * �ж�Key�Ƿ����
     * 
     * @param key
     * @return
     */
    public boolean containsKey(T key) {
        return interGet(root, key) != null;
    }

    /**
     * ����Keyɾ��
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
        if (!node.occupy) {// �ڵ�û�б�ռ�ã�ʹ��
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

        if (node.occupy && node.entry.key.equals(key)) {// �ڵ㱻ռ��
            return node;
        }

        int index = Math.abs(key.hashCode() % LEVEL[node.level]);
        return interGet(node.points[index], key);
    }

    static class TreeNode<M, N> {
        private boolean occupy;// �Ƿ�ռ��
        private int level;// �ڵ���������
        private Entry<M, N> entry;// �ڵ�
        private TreeNode<M, N>[] points;// ��̽ڵ�ָ��

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

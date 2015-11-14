package leamon.datastruct.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ǰ׺����Hash���ı���
 */
public class TrieTree {

    private static final int SIZE = 128;// ascii��֧��

    private TreeNode root;// �����ڵ�

    public TrieTree() {
        root = new TreeNode();
    }

    /**
     * ���
     * 
     * @param str
     */
    public void insert(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        TreeNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            int index = letters[i];
            if (node.points[index] == null) {
                node.points[index] = new TreeNode();
                node.points[index].value = letters[i];
            } else {
                node.points[index].count++;
            }
            node = node.points[index];
        }
        node.over = true;
    }

    /**
     * ȡǰ׺Ϊ�����ַ���������
     * 
     * @param prefix
     * @return
     */
    public int prefixCount(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return -1;
        }
        TreeNode node = root;
        char[] letters = prefix.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            int index = letters[i];
            if (node.points[index] == null) {
                return 0;
            }
            node = node.points[index];
        }

        return node.count;
    }

    /**
     * �Ƿ���������ַ���
     * 
     * @param str
     * @return
     */
    public boolean contains(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        TreeNode node = getTreeNode(str);

        return node == null ? false : node.over;
    }

    /**
     * ȡ��ͬǰ׺�ַ����б�
     * 
     * @param prefix
     * @return
     */
    public List<String> getPrefix(String prefix) {
        TreeNode node = getTreeNode(prefix);
        List<String> result = new ArrayList<>();

        interHandle(node, prefix.substring(0, prefix.length() - 1), result);
        return result;
    }

    /**
     * ��ȡ�����ַ����������еĽڵ�
     * 
     * @param str
     * @return
     */
    private TreeNode getTreeNode(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        TreeNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            int index = letters[i];
            if (node.points[index] == null) {
                return null;
            }
            node = node.points[index];
        }
        return node;
    }

    private void interHandle(TreeNode node, String prefix, List<String> list) {
        if (node != null) {
            prefix += node.value;
            for (TreeNode point : node.points) {
                interHandle(point, prefix, list);
            }
            if (node.over) {
                list.add(prefix);
            }
        }
    }

    static class TreeNode {// ���ڵ�
        private char value;
        private boolean over;
        private int count = 1;
        private TreeNode[] points = new TreeNode[SIZE];// ��̽ڵ�ָ��
    }

    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("D:/apache-tomcat-6.0.44/conf/server.xml"));
            String line = reader.readLine();
            while (line != null) {
                String[] strs = line.split(" ");
                for (String str : strs) {
                    System.out.println(str);
                    tree.insert(str);
                }

                line = reader.readLine();
            }
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(tree.getPrefix("className"));
    }

}

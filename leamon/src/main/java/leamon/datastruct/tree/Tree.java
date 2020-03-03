package leamon.datastruct.tree;

/**
 * Tree �ӿ�
 */
public interface Tree {

    /**
     * �������
     * 
     * @param data
     */
    public void inside(int data);

    /**
     * ɾ������
     * 
     * @param data
     */
    public void delete(int data);

    /**
     * �Ƿ��������
     * 
     * @param data
     * @return
     */
    public boolean contain(int data);

    /**
     * ���Ҳ���
     * 
     * @param data
     * @return
     */
    public TreeNode search(int data);

    /**
     * �������root
     * 
     * @return
     */
    public TreeNode getRoot();

    /**
     * ��ӡ����
     */
    public void print();

    /**
     * ��ȡ������
     * 
     * @return
     */
    public Tree getLeftTree();

    /**
     * ��ȡ������
     * 
     * @return
     */
    public Tree getRightTree();

    /**
     * ���ڵ�ӿ�
     */
    interface TreeNode {

        public TreeNode getLeft();

        public TreeNode getRight();

    }
}

package leamon.datastruct.tree;

/**
 * Tree 接口
 */
public interface Tree {

    /**
     * 插入操作
     * 
     * @param data
     */
    public void inside(int data);

    /**
     * 删除操作
     * 
     * @param data
     */
    public void delete(int data);

    /**
     * 是否存在数据
     * 
     * @param data
     * @return
     */
    public boolean contain(int data);

    /**
     * 查找操作
     * 
     * @param data
     * @return
     */
    public TreeNode search(int data);

    /**
     * 获得树的root
     * 
     * @return
     */
    public TreeNode getRoot();

    /**
     * 打印操作
     */
    public void print();

    /**
     * 获取左子树
     * 
     * @return
     */
    public Tree getLeftTree();

    /**
     * 获取右子树
     * 
     * @return
     */
    public Tree getRightTree();

    /**
     * 树节点接口
     */
    interface TreeNode {

        public TreeNode getLeft();

        public TreeNode getRight();

    }
}

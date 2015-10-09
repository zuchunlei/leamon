package leamon.datastruct.tree;

public class TreeMainClient {

    public static void main(String[] args) {
        int[] numbers = { 50, 10, 28, 888, 333, 222, 444, 656, 1, 30, 8, 12, 45, 89, 75, 2, 98, 310, 470, 52, 80, 109,
                320, 69, 100, 207, 982, 5, 67 };
        // int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        // int[] numbers = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

        Tree tree = new AVLTree();

        for (int number : numbers) {
            tree.inside(number);
        }

        Tree rightTree = tree.getRightTree();
        rightTree.print();

        tree.delete(50);

        boolean flag = tree.contain(30);
        System.out.println(flag);

        tree.print();

    }
}

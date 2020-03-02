package leamon.datastruct.heap;

/**
 * 从给定的数据L中找出第K小的数
 */
public class HeapMainClient {

    public static void main(String[] args) {
        // 从number中取出第7小的数值
        int[] numbers = { 50, 10, 28, 888, 333, 222, 444, 656, 1, 30, 8, 12, 45, 89, 75, 2, 98, 310, 470, 52, 80, 109,
                320, 69, 100, 207, 982, 5, 67 };

        MinimumHeap heap = new MinimumHeap(numbers);

        int result = 0;
        for (int i = 0; i < 7; i++) {
            result = heap.deleteMin();
            // System.out.println(result);
        }

        System.out.println("numbers中第7小的为:" + result);

        // numbers已经进行了排序
        MinimumHeap.heapSort(numbers);
    }
}

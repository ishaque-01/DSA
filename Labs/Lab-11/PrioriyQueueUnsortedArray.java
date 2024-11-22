// Using Unsorted Array
class PriorityQueue {

    private int[] arr;
    private int capacity, size;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        size = -1;
        arr = new int[capacity];
    }

    public void insert(int data) {
        if (size == capacity - 1) {
            System.out.println("Priority Queue is Full!");
            return;
        } else {
            arr[++size] = data;
        }
    }

    public int extractMax() {
        int max = Integer.MIN_VALUE, idx = -1;
        // Storing Max Number in max variable and its index to shift further elements
        for (int i = 0; i <= size; i++) {
            if (arr[i] > max) {
                max = arr[i];
                idx = i;
            }
        }

        for (; idx < size; idx++) {
            arr[idx] = arr[idx + 1];
        }
        size--;
        return max;
    }

    public int getMax() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i <= size; i++) {
            max = arr[i] > max ? arr[i] : max;
        }
        return max;
    }

    public boolean searchData(int data) {
        for (int i = 0; i <= size; i++) {
            if(arr[i] == data)
                return true;
        }
        return false;
    }
}

public class PrioriyQueueUnsortedArray {
    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue(10);

        priorityQueue.insert(12);
        priorityQueue.insert(15);
        priorityQueue.insert(2);
        priorityQueue.insert(33);
        priorityQueue.insert(24);

        System.out.println("Extracting Max Value: " + priorityQueue.extractMax());
        System.out.println("Getting Max Value: " + priorityQueue.getMax());

        System.out.println("Searching 33: " + priorityQueue.searchData(33));

    }
}
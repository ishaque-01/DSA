// Using Sorted Array
class PriorityQueue {

    private int[] arr;
    private int capacity, size;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new int[capacity];
    }

    public void insert(int data) {
        if (size == capacity) {
            throw new RuntimeException("Priority Queue is Full!");
        } else {
            int idx = size;
            int low = 0, high = size - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (arr[mid] < data)
                    low = mid + 1;
                else {
                    idx = mid;
                    high = mid - 1;
                }
            }
            for (int i = size; i > idx; i--) {
                arr[i] = arr[i - 1];
            }
            arr[idx] = data;
            size++;
        }
    }

    public int extractMax() {
        return arr[--size];
    }

    public int getMax() {
        return arr[size - 1];
    }

    public boolean searchData(int data) {
        int low = 0, high = size;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == data)
                return true;
            if (arr[mid] < data)
                low = mid + 1;
            if (arr[mid] > data) {
                high = mid - 1;
            }
        }
        return false;
    }

    public void printQueue() {
        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

public class PriorityQueueSortedArray {
    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue(10);

        priorityQueue.insert(15);
        priorityQueue.insert(12);
        priorityQueue.insert(33);
        priorityQueue.insert(2);
        priorityQueue.insert(24);

        priorityQueue.printQueue();
        System.out.println();

        System.out.println("Get Max: " + priorityQueue.getMax());
        System.out.println("Extract Max: " + priorityQueue.extractMax());
        System.out.println("Searching 24: " + priorityQueue.searchData(24) );

    }
}

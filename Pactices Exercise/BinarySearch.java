public class BinarySearch {
    public static void main(String[] args) {

        // Binary Search = Search algorithm that finds the position of a target value
        // within a sorted array.
        // Half of the array is eliminate during each "step"
        // runtime : O(log n)

        int[] array = new int[100];

        int target = 45;

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int index = binarySearch(array, target);

        if (index != -1)
            System.out.println(target + " found at: " + index);
        else
            System.out.println("Target not Found");

    }

    private static int binarySearch(int[] array, int target) {

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {

            int middle = (low + high) / 2;

            int value = array[middle];

            if (value < target)
                low = middle + 1;

            else if (value > target)
                high = middle - 1;

            else
                return middle;
        }

        return -1;
    }
}
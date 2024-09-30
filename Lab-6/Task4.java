public class Task4 {
    public static void main(String[] args) {

        int[] arr = { 44, 52, 65, 12, 33, 9, 8 };

        insertionSort(arr);

        for (int i : arr) {
            System.out.print( i + " ");
        }System.out.println();
        twoSum(arr, 1000);

    }

    private static void insertionSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    private static void twoSum(int[] arr, int target) {

        int left = 0, right = arr.length - 1;
        boolean check = true;
        while(left < right) {
            int sum = arr[left] + arr[right];
        
            if(sum == target) {
                check = false;
                System.out.println("1st number is at index: " + left + " and 2nd number is at: " + right);
                break;
            } else if(sum < target) {
                left++;
            } else {
                right--;
            }
        }

        if(check) {
            System.out.println("Not Found");
        }

    }
}

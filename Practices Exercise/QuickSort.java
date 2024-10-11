public class QuickSort{
    public static void main(String[] args) {
        
        int[] array = {2,32,512,1024,99,64,128,25,4,8,16,10};

        quickSort(array, 0, array.length -1);

        for(int i:array)
            System.out.print(i + " ");
    }

    private static void quickSort(int[] array, int start, int end) {
    
        if(start >= end) return;

        int pivot = partition(array, start, end);

        quickSort(array, start, pivot - 1);
        quickSort(array, pivot + 1, end);
    }

    private static int partition(int[] array, int start, int end) {

        // pivot at end
        int pivot = array[end];
        int i = start - 1;
        for(int j=start; j<end; j++){
            if(pivot > array[j]){
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        i++;
        int temp = array[end];
        array[end] = array[i];
        array[i] = temp;


        // Pivot at start
/* 
        int i = end + 1;
        int pivot = array[start];

        for(int j = end; j > start; j--){
            if(array[j] > pivot){
                i--;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        i--;
        int temp = array[i];
        array[i] = array[start];
        array[start] = temp;
*/
        return i;
    }
}
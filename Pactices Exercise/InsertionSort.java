public class InsertionSort {
    public static void main(String[] args) {
     
        //  Insertion Sort = After comparing elements to the left
        //                   shift elements to the right to make room to insert a value
        
        //                   Quardatic time O(n^2)
        //                   small data set = decent
        //                   large data set = BAD
        
        //                   Less Steps then Bubble Sort
        //                   Best Case is O(n) comapred to Selection Sort O(n^2)

        int[] array = {9,1,8,2,7,3,6,5,4};

        System.out.println("Unsorted Array");
        for (int i : array) {
            System.out.print(i + " ");
        }

        //-----------------Insertion Sort--------------------------

        for(int i=1; i<array.length; i++){
            int temp = array[i];
            int j = i - 1;
            while(j >= 0 && array[j] > temp){
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        }

        //-----------------Insertion Sort--------------------------

        System.out.println("\nSorted Array");
        for(int i:array){
            System.out.print(i + " ");
        }

    }
}

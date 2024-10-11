public class BubbleSort {
    public static void main(String[] args) {
     
            //  Bubble Sort = pairs of adjacent elements are compared, and the elements 
            //                swapped if they are not in order

            //                Quadratic time O(n^2)
            //                small data set = okay-ish
            //                large data set = bad
            
            int[] array = {4,5,2,6,9,7,8,1,3};

            //--------Unsorted Array-------------
            System.out.println("Unsorted Array");
            
            for(int a:array){
                System.out.print(a + " ");
            }

            //---------Bubble Sort----------------
            for(int i=0; i<array.length - 1; i++){
                for(int j=0; j<array.length - i - 1; j++){
                    
                    if(array[j] > array[j+1]){

                        array[j] = array[j] ^ array[j+1];
                        array[j + 1] = array[j] ^ array[j+1];
                        array[j] = array[j] ^ array[j+1];
                    }
                }
            }
            
            //--------Sorted Array-------------
            System.out.println("\nSorted Array");
            
            for(int a:array){
                System.out.print(a + " ");
            }
            
            System.out.println();
    }
}

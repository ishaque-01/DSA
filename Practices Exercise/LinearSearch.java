public class LinearSearch {
    public static void main(String[] args) {
        

        //  Linear Search = Iterate through a collection one element at a time 
        
        //                  runtime complexity: O(n)

        //                  Disadvantages:
        //                  Slow for large data sets

        //                  Advantages:
        //                  Fast for searches of small to medium data sets
        //                  Does not need to sorted
        //                  Useful for data structures that do not have random access (Linked List)

        int[] array = {4,6,7,2,1,5,9,3,8};
        int index = linearSearch(array, 1);

        if(index != -1){
            System.out.println("Element found at index: " + index);
        }else{
            System.out.println("Element not found in the array");
        }

    }

    private static int linearSearch(int[] array, int value) {
        for(int i=0; i<array.length; i++){
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }
}

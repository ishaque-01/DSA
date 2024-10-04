public class MergeSort{
    public static void main(String[] args) {

        //  Merge Sort = recursively divide array in 2 sub arrays, sort them and re-combine them
        //  run-time complexity  = O(n log n)
        //  space complexity     = O(n)

        int[] array = {5,6,4,2,8,1,9,7};

        mergeSort(array);

        for (int i : array) {
            System.out.print(i + " ");
        }System.out.println();
    }

    private static void mergeSort(int[] array) {
        
        int length = array.length;

        if(length <= 1) return;

        int mid = length / 2;
        
        int[] leftArray = new int[mid];
        int[] rightArray = new int[length - mid];


        int i = 0; //left array
        int j = 0; // rigth array


        for(; i<length; i++){
            if(i < mid){
                leftArray[i] = array[i];
            }else{
                rightArray[j] = array[i];
                j++;
            }
        }

        mergeSort(leftArray);
        mergeSort(rightArray);

        merging(leftArray, rightArray,array);

    }

    private static void merging(int[] leftArray, int[] rightArray, int[] array) {

        int leftSize = array.length / 2;
        int rightSize = array.length - leftSize;
        
        int i = 0, l = 0, r = 0; //Original array(i), LeftArray(l),  RightArray(r)

        while(l < leftSize && r < rightSize){
            if(leftArray[l] < rightArray[r]){
                array[i] = leftArray[l];
                i++; l++;
            }else{
                array[i] = rightArray[r];
                i++; r++;
            }
        }

        while(l < leftSize){
            array[i] = leftArray[l];
            i++; l++;        
        }

        while(r < rightSize){
            array[i] = rightArray[r];
            i++; r++;       
        }
    }
}
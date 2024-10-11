public class Recursion {

    // Recursion = When a thing is defined in terms of itself. -Wikipedia
    //             Apply the result of a procedure, to a procedure.
    //             A recursive method calls itself. Can be a substitute for iteration.
    //             Divide a problem into sub-problems of the same type as the original.
    //             Commanly used with advanced sorting algorithms and navigating trees.

    //             Advantages
    //             easier to read/write
    //             easier to debug

    //             disadvantages
    //             sometimes slower
    //             uses more memory
    public static void main(String[] args) {
        
        walk(5);

    }
    private static void walk(int steps){

        if(steps < 1) return;

        System.out.println("You take a step!");

        walk(steps - 1);
    }
    
}

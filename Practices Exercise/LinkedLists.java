import java.util.LinkedList;

public class LinkedLists {
    public static void main(String[] args) {
        
        /*
            LinkedList = stores Nodes in 2 parts (data + address)
                         Nodes are in non-consecutive memory location
                         Elements are linked using pointers


                                    Singly Linked List
                      Node               Node                Node
                [data | address] -> [data | address] -> [data | address]


                                                Doubly Linked List
                            Node                        Node      
                [address | data | address] <--> [address | data | address] <--> [address | data | address]


                advantages?
                1.Dynamic Data Structure (allocates needed memory while running)
                2. Insertion and Deletion of Nodes is easy. O(1)
                3. No/Low memory waste

                disadvantages?
                1. Greater memory usage (additional pointers)
                2. No random access of elements (no index[i])
                3. Accessing/Searching elements is more time consuming. O(n)


                uses?
                1. Implements Stacks/Queues
                2. GPS navigation
                3. Music Playlist
         */


        LinkedList<String> LinkedList = new LinkedList<String>();

        //use as a Stack
        /* 
        LinkedList.push("A");
        LinkedList.push("B");
        LinkedList.push("C");
        LinkedList.push("D");
        LinkedList.push("F");
        LinkedList.pop();
        */

        //use  as a queue
        LinkedList.offer("A");
        LinkedList.offer("B");
        LinkedList.offer("C");
        LinkedList.offer("D");
        LinkedList.offer("F");

        // LinkedList.poll();

        //adding element at index 4
        LinkedList.add(4,"E");
        LinkedList.remove("E");//removeing an element

        //System.out.println(LinkedList.indexOf("F"));//Find Index

        System.out.println("Peek at First Node: " + LinkedList.peekFirst());
        System.out.println("Peek at Last Node: " + LinkedList.peekLast());

        LinkedList.addFirst("0");
        LinkedList.addLast("G");

        String first = LinkedList.removeFirst(), last = LinkedList.removeLast();
        System.out.println("First : " + first + "\nLast : " + last);



        System.out.println(LinkedList);

    }
    
}

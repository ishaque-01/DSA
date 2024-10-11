class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        next = null;
    }
}

class LinkedList {

    Node head;

    public void add(int data) {
        Node node = new Node(data);

        if(head == null) {
            head = node;
        } else {
            Node temp = head;
            while(temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
    }
}

public class Task6b {
    
    private static void printListAscending(Node head) {
        if(head == null) {
            return;
        }
        System.out.print(head.data + " ");
        printListAscending(head.next);
    }

    private static void printListDescending(Node head) {
        if(head == null) {
            return;
        }
        printListDescending(head.next);
        System.out.print(head.data + " ");
    }

    public static void main(String[] args) {
        
        LinkedList ll = new LinkedList();

        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);


        long startTime = System.currentTimeMillis();

        System.out.println("List in ascending order");
        printListAscending(ll.head);
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total Time: " + totalTime + "ms");




        startTime = System.currentTimeMillis();

        System.out.println("\nList in descending order");
        printListDescending(ll.head);
        System.out.println();
        
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Total Time: " + totalTime + "ms");




    }
}

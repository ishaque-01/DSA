import java.util.Stack;

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

        if (head == null) {
            head = node;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
    }
}

public class Task6a {
    public static void main(String[] args) {

        LinkedList ll = new LinkedList();

        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);


        Node temp = ll.head;

        long startTime = System.currentTimeMillis();

        System.out.println("Ascending Order:");
        while (temp.next != null) {
            System.out.print(temp.data + " --> ");
            temp = temp.next;
        }
        System.out.println(temp.data);
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total Time: " + totalTime + "ms");

        

        temp = ll.head;

        startTime = System.currentTimeMillis();

        System.out.println("Descending Order:");
        
        Stack<Integer> stack = new Stack<>();

        while (temp != null) {
            stack.push(temp.data);
            temp = temp.next;
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Total Time: " + totalTime + "ms");

    }
}
import java.util.HashMap;

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        next = null;
    }
}

class LinkedList {
    Node head, tail;
    int size;

    public void add(int data) {
        Node node = new Node(data);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void addFirst(int data) {
        Node node = new Node(data);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    public void printList() {
        if (isEmpty()) {
            System.out.println("List is Empty!");
        } else {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
        }
        System.out.println();
    }

    public void delete() {
        if (isEmpty()) {
            System.out.println("List Empty!");
        } else {
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            temp.next = null;
            size--;
        }
    }

    public void deleteFirst() {
        if (isEmpty()) {
            System.out.println("List Empty!");
        } else {
            head = head.next;
            size--;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }

    public void makeLoop(int data) {
        Node temp = head;

        while (temp != null) {
            if (temp.data == data)
                break;

            temp = temp.next;
        }

        tail.next = temp;
    }
}

public class LinkedListLoop {
    public static void main(String[] args) {

        LinkedList ll = new LinkedList();

        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);

        ll.printList();

        // ll.makeLoop(3);

        System.out.println("Looped By HashMap: " + isLoopByMap(ll.head));

        System.out.println("Looped By Floyd's Algo: " + isLoop(ll.head));


    }

    private static boolean isLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    private static boolean isLoopByMap(Node head) {

        HashMap<Node, Integer> map = new HashMap<>();

        Node temp = head;

        while (temp != null) {
            if (map.containsKey(temp)) {
                return true;
            } else {
                map.put(temp, 1);
                temp = temp.next;
            }
        }
        return false;
    }
}

import java.util.Scanner;

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
    int size = 0;

    public boolean searchData(int data) {
        if (head == null)
            System.out.println("List is Empty!");
        else {
            Node temp = head;
            while (temp.next != null) {
                if (temp.data == data)
                    return true;
                temp = temp.next;
            }
        }
        return false;
    }

    public void add(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.next = newNode;
            tail = tail.next;
            size++;
        }
    }

    public void addFirst(int data) {
        Node node = new Node(data);
        node.next = head;
        head = node;
        size++;
    }

    public void removeFirst() {
        if (head == null)
            System.out.println("List is Empty!");
        else {
            head = head.next;
            size--;
        }
    }

    public void removeLast() {
        if (head == null)
            System.out.println("List is Empty!");
        else {
            Node node = head;
            while (node.next.next != null) {
                node = node.next;
            }
            node.next = null;
            tail = node;
            size--;
        }
    }

    public void printList() {
        Node node = head;
        while (node.next != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println(node.data);
    }

    public Boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }

    // ------------------TASK-1------------------
    public int nthFromLast(int n) {

        if (head == null)
            System.out.println("List is empty!");

        Node temp = head;
        int count = getSize() - n;
        while (count != 0) {
            temp = temp.next;
            count--;
        }

        count = temp.data;
        return count;
    }
    // ------------------TASK-1------------------

}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LinkedList ll = new LinkedList();

        ll.add(3);
        ll.add(5);
        ll.add(2);
        ll.add(4);
        ll.add(1);

        ll.printList();

        System.out.print("Enter number: ");
        int n = sc.nextInt();

        System.out.println("The nth node data is: " + ll.nthFromLast(n));

        sc.close();

        ll.head = sort(ll.head);

        System.out.println("Sorted List: ");
        ll.printList();

    }

    // ------------------TASK-2------------------
    public static Node sort(Node head) {

        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head);
        Node middleNext = middle.next;
        middle.next = null;

        Node left = sort(head);
        Node right = sort(middleNext);

        return merge(left, right);
    }

    private static Node merge(Node left, Node right) {
        if (left == null)
            return right;
        if (right == null)
            return left;

        Node dummy = new Node(0);
        Node current = dummy;

        while (left != null && right != null) {
            if (left.data < right.data) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }

        current.next = (left != null) ? left : right;

        return dummy.next;
    }

    private static Node getMiddle(Node head) {

        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // ------------------TASK-2------------------
}
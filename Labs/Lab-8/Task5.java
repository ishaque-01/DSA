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
        } // Task-2 Length

        System.out.println(node.data);
    }

    public Boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }
}

public class Task5 {
    public static void main(String[] args) {

        LinkedList l1 = new LinkedList();

        l1.add(14);
        l1.add(22);
        l1.add(35);
        l1.add(45);
        l1.add(55);


        LinkedList l2 = new LinkedList();

        l2.add(61);
        l2.add(99);
        l2.add(105);

        Node merged = mergeSortedLists(l1, l2);

        while (merged != null) {
            System.out.print(merged.data + " ");
            merged = merged.next;
        }
        System.out.println();

    }

    private static Node mergeSortedLists(LinkedList l1, LinkedList l2) {

        Node temp1 = l1.head, temp2 = l2.head;
        if (temp1 != null && temp2 != null) {

            LinkedList mergedList = new LinkedList();
            while (temp1.next != null && temp2.next != null) {
                if (temp1.data <= temp2.data) {
                    mergedList.add(temp1.data);
                    temp1 = temp1.next;
                } else {
                    mergedList.add(temp2.data);
                    temp2 = temp2.next;
                }
            }

            if (temp1.data <= temp2.data) {
                mergedList.add(temp1.data);
            }

            while (temp1.next != null) {
                mergedList.add(temp1.data);
                temp1 = temp1.next;
            }

            while (temp2.next != null) {
                mergedList.add(temp2.data);
                temp2 = temp2.next;
            }

            mergedList.add(temp2.data);

            return mergedList.head;
        }

        else if (temp1 == null && temp2 != null) {
            return temp2;
        } else if (temp1 != null && temp2 == null) {
            return temp1;
        } else {
            return null;
        }

    }
}

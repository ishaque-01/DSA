public class LinkedList <T>{
    class Node{
        T data;
        Node prev, next;
        public Node(T data) {
            this.data = data;
            prev = null;
            next = null;
        }
    }
    Node head, tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public void add(T data) {
        Node node = new Node(data);
        if(isEmpty()) {
           head =  node;
           tail = node;
        } else {
            tail.next = node;
        }
    }

    public void print() {
        Node temp = head;
        while(temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    public boolean find(T data) {
        if(head == null) {
            return false;
        } else {
            Node temp = head;
            while(temp != null) {
                if(temp.data.equals(data)){
                return true;
                }
                temp = temp.next;
            }
        return false;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

}

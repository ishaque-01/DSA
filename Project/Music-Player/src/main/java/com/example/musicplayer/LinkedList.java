package com.example.musicplayer;

import java.io.File;

public class LinkedList {
    static class Node {
        File data;
        Node next, prev;
        public Node(File data) {
            this.data = data;
            next = null;
            prev = null;
        }
    }
    Node head, tail;
    public LinkedList() {
        head = null;
        tail = null;
    }

    public void add(File file) {
        Node newNode = new Node(file);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            if (file.getName().compareTo(head.data.getName()) < 0) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else if (file.getName().compareTo(tail.data.getName()) > 0) {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            } else {
                Node current = head;
                while (current.next != null && file.getName().compareTo(current.next.data.getName()) > 0) {
                    current = current.next;
                }
                newNode.prev = current;
                newNode.next = current.next;
                current.next.prev = newNode;
                current.next = newNode;
            }
        }
    }
    public boolean find(String fileName) {
        Node current = head;
        while (current != null) {
            if (current.data.getName().equals(fileName)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public String print() {
        Node temp = head;
        int count = 1;
        StringBuilder sb = new StringBuilder();
        while(temp != null) {
            sb.append(count).append(". ").append(temp.data.getName()).append("\n\n");
            temp = temp.next;
            count++;
        }
        return sb.toString();
    }


}
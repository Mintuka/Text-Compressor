package com.codeWithMinte;

public class LinkedList {

    public LinkedListNode head = null;
    public LinkedListNode tail = null;

    public boolean isEmpty() {
        if (head == null && tail == null)
            return true;
        return false;
    }

    public void insert(Object Char) {
        if (isEmpty())
            head = tail = new LinkedListNode(Char);
        else {
            tail.next = new LinkedListNode(Char);
            tail = tail.next;
        }
    }

    public void delete() {
        if (isEmpty())
            head = tail = null;
        else if(head == tail)
            head = tail = null;
        else {
            head = head.next;
        }
    }

    public boolean search(String Char) {
        if (isEmpty())
            return false;
        LinkedListNode val = this.head;
        for (; !val.Char.equals(Char) && val.next != null ; val = val.next);
        if (val.Char.equals(Char))
            return true;
        return false;
    }
    }


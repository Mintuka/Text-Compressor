package com.codeWithMinte;

public class LinkedListNode {

    public Object Char;
    public int count;
    public LinkedListNode next;

    public LinkedListNode(Object Char){
        this.Char = Char;
        this.count += 1;
        this.next = null;
    }

    public LinkedListNode(Object Char,LinkedListNode next){
        this.Char = Char;
        this.count += 1;
        this.next = next;
    }

}

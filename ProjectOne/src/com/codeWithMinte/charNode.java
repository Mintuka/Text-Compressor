package com.codeWithMinte;

public class charNode {
    public String Char;
    public int freq;
    public charNode left;
    public charNode right;

    public charNode(String Char,int freq,charNode left,charNode right){
        this.Char = Char;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public charNode(String Char,int freq){
        this.Char = Char;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    public void listOfChar() {
        LinkedListNode charList;
    }
}

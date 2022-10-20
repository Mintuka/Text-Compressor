package com.codeWithMinte;

import java.util.ArrayList;
import java.util.Scanner;

public class Huffman {
    charNode root = null;
    LinkedList list = new LinkedList();
    ArrayList<LinkedList> codeWord;

    public void List() {
        fileManagement fileReader = new fileManagement();
        String fileData = (fileReader.readOneFile("freq.txt")).strip();
        String[] charArray = fileData.split("\n");
        String[] Char = new String[2];
        //list the a node for each character
        for (String word : charArray) {
            Char = word.split("-");
            LinkedList temp = new LinkedList();
            for (int i = 0; i < Char.length; i++)
                temp.insert(Char[i]);
            if (!temp.head.Char.toString().equals(""))
                list.insert(new charNode(temp.head.Char.toString(), Integer.parseInt(temp.tail.Char.toString())));
            else {
                list.insert(new charNode("\n", Integer.parseInt(temp.tail.Char.toString())));
            }
        }


        while (list.head != list.tail) {
            //sort the nodes in ascending order
            for (LinkedListNode m = list.head; m != null; m = m.next)
                for (LinkedListNode n = m.next; n != null; n = n.next)
                    if (((charNode) m.Char).freq > ((charNode) n.Char).freq) {
                        charNode temp = ((charNode) n.Char);
                        n.Char = ((charNode) m.Char);
                        m.Char = temp;
                    }
            //create tree with two nodes at first location and delete them
            int freq = ((charNode) list.head.Char).freq + ((charNode) list.head.next.Char).freq;
            String Conc = ((charNode) list.head.Char).Char + ((charNode) list.head.next.Char).Char;

            list.insert(new charNode(Conc, freq, ((charNode) list.head.Char), ((charNode) list.head.next.Char)));
            int j = 0;
//            for (LinkedListNode N = list.head; N != list.tail; N = N.next, j++)
//                System.out.print(((charNode) N.Char).Char + "-" + ((charNode) N.Char).freq + ",");
//            System.out.println();
//            System.out.print("  Par: " + ((charNode) list.tail.Char).Char + "-" + ((charNode) list.tail.Char).freq + " <> L:");
//            System.out.print(((charNode) list.tail.Char).left.Char + "-" + ((charNode) list.tail.Char).left.freq + " <> R:");
//            System.out.print(((charNode) list.tail.Char).right.Char + "-" + ((charNode) list.tail.Char).right.freq);
//            System.out.println("!---!" + j + "!---!");
            for (int i = 0; i < 2; i++)
                list.delete();
        }
        root = ((charNode) list.head.Char);
        System.out.println("root: " + root.Char + "-" + root.freq);
        charNode temp = root;
        breadthFirst();
/*        while (temp != null) {
            System.out.println("lefts : "+temp.Char + "-" + temp.freq);
            temp = temp.left;
        }*/
    }

    public void breadthFirst() {
        fileManagement fileWriter = new fileManagement();
        charNode p = root;
        LinkedList queue = new LinkedList();
        codeWord = new ArrayList<>();
        String[] Char = root.Char.split("");
        int i = 0;
        for (String code : Char) {
            codeWord.add(new LinkedList());
            codeWord.get(i).insert(code);
            i++;
        }
        if (p != null) {
            queue.insert(p);
            System.out.println("P NOT NULL");
            while (!queue.isEmpty()) {
                p = ((charNode) queue.head.Char);
                queue.delete();
                i = 0;
                i++;
               // System.out.print("--" + i + "--" + p.Char);
                if (p.left != null) {
                    queue.insert(p.left);
                    Char = p.left.Char.split("");
                    for (String word : Char)
                        for (i = 0; i < codeWord.size(); i++)
                            if (word.equals(codeWord.get(i).head.Char))
                                if (codeWord.get(i).head == codeWord.get(i).tail)
                                    codeWord.get(i).insert("0");
                                else {
                                    codeWord.get(i).tail.Char = codeWord.get(i).tail.Char + "0";
                                }
                }
                if (p.right != null) {
                    queue.insert(p.right);
                    Char = p.right.Char.split("");
                    for (String word : Char)
                        for (i = 0; i < codeWord.size(); i++)
                            if (word.equals(codeWord.get(i).head.Char))
                                if (codeWord.get(i).head == codeWord.get(i).tail)
                                    codeWord.get(i).insert("1");
                                else {
                                    codeWord.get(i).tail.Char = codeWord.get(i).tail.Char + "1";
                                }
                }
            }
        }
        while (p != null) {
            System.out.println(p.Char);
            p = p.left;
        }
        for (i = 0; i < codeWord.size(); i++) {
            fileWriter.writeToAFile("codeGenerated.txt",codeWord.get(i).head.Char + " - "+codeWord.get(i).head.next.Char,true);
            System.out.print(" [" + codeWord.get(i).head.Char + "->" + codeWord.get(i).head.next.Char + "], ");
        }
        replace();
    }

    public void replace() {
        System.out.println();
        System.out.println("Enter the file name to be compressed please");
        //Accept the user the file Name
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();
        //create character array
        fileManagement fileReader = new fileManagement();
        fileManagement fileWriter = new fileManagement();
        String fileData = (fileReader.readOneFile(fileName)).strip();
        String[] charArray = fileData.split("");
        //create frequency table
        LinkedList list = new LinkedList();
        for (String Char : charArray) {
            for (int i = 0; i < codeWord.size(); i++)
                if (Char.equals(codeWord.get(i).head.Char))
                    fileWriter.writeToAFile("compressed.txt", codeWord.get(i).tail.Char.toString(), true);
        }
        compress();
    }

    public void compress() {
        String outPutFile = "output.txt";

        manageCompressedFile compress = new manageCompressedFile();
        //fill the bitString
        fileManagement fileReader = new fileManagement();
        String fileString = (fileReader.readOneFile("compressed.txt")).strip();
        String[] array = fileString.split("\n");
        LinkedList list = new LinkedList();
        for (String word : array)
            list.insert(word);
        int i = 0;
        String bitString = "0";
        for (LinkedListNode N = list.head; N != null; N = N.next, i++) {
            bitString = bitString + N.Char;
        }
        System.out.println("File: " + "Size: " + i +"-" +bitString.length()+"-" + list.tail.Char + "<>" + bitString);

        //fill symbols array
        char[] symbols = new char[codeWord.size()];
        for (i = 0; i < codeWord.size(); i++)
            symbols[i] = codeWord.get(i).head.Char.toString().charAt(0);
        //fill codeForSymbols
        String[] codeForSymbols = new String[codeWord.size()];
        for (i = 0; i < codeWord.size(); i++)
            codeForSymbols[i] = codeWord.get(i).tail.Char.toString();
        //compress
        compress.writeACompressedFile(bitString, symbols, codeForSymbols, outPutFile);
    }
}
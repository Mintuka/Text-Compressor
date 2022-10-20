package com.codeWithMinte;

import java.util.Scanner;

public class Frequency {

    public void frequency() {
        System.out.println("Enter the file name to find frequency "+"\n"+"like this ---> input.txt");
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
            if (list.search(Char)) {
                LinkedListNode inc = list.head;
                for (; !inc.Char.equals(Char); inc = inc.next) ;
                inc.count += 1;
            } else {
                list.insert(Char);
            }
        }

        for (LinkedListNode inc = list.head; inc != null; inc = inc.next) {
            if(inc.Char.toString().charAt(0) != '\n')
                fileWriter.writeToAFile("freq.txt",inc.Char+"-"+inc.count,true);
            else {
                fileWriter.writeToAFile("freq.txt",""+"-"+inc.count,true);
            }
        }
    }
}

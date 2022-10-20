package com.codeWithMinte;

public class Main {
    static Frequency freq = new Frequency();
    static Huffman huff = new Huffman();
    static Decompress decomp = new Decompress();

    public static void main(String[] args) {
        fileManagement fileWriter = new fileManagement();
        fileWriter.writeToAFile("codeGenerated.txt","");
        fileWriter.writeToAFile("compressed.txt","");
        fileWriter.writeToAFile("decompressed.txt","");
        fileWriter.writeToAFile("freq.txt","");
        fileWriter.writeToAFile("output.txt","");

        freq.frequency();
        huff.List();
        decomp.decompress();
    }
}

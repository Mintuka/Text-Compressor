package com.codeWithMinte;

public class Decompress {
    public void decompress() {
        fileManagement fileWriter = new fileManagement();
        manageCompressedFile decomp = new manageCompressedFile();
        CompressedContent compress = decomp.readCompressedFile("output.txt");
        //System.out.println(compress.bitString.length()+"-->"+compress.bitString);

//        for (int i = 0; i < compress.symbols.length; i++)
//            System.out.print(compress.symbols[i]);
//        System.out.println();
//        for (int i = 0; i < compress.codeForSymbols.length; i++)
//            System.out.print(i + "->" + compress.codeForSymbols[i] + "-");

        String temp = "";
        String Final = "";
        int beginIndex = 0;
        for (int endIndex = beginIndex + 1; endIndex < compress.bitString.length(); endIndex++) {
            temp = compress.bitString.substring(beginIndex, endIndex);
            for (int i = 0; i < compress.codeForSymbols.length; i++)
                if (temp.equals(compress.codeForSymbols[i])) {
                    Final = Final + compress.symbols[i];
                    beginIndex = endIndex;
                    temp = "";
                }
        }
        System.out.println();
        //System.out.println("Final: "+compress.bitString.length()+"-->"+Final);
        fileWriter.writeToAFile("decompressed.txt",Final);
    }

}

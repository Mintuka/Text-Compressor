package com.codeWithMinte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class manageCompressedFile {

    public int numberOfLines = 6;

    public List<String> stringToByte(String str) {
        List<String> byteStrings = new ArrayList<String>();
        for (int i = str.length(); i > -1; i = i - 8) {
            if ((i - 8) > -1) {
                byteStrings.add(0, str.substring(i - 8, i).strip());
            } else {
                byteStrings.add(0, str.substring(0, i).strip());
            }
        }
        return byteStrings;
    }

    public char binaryStringToChar(String byteString) {
        int charCode = Integer.parseInt(byteString, 2);
        char charValue = (char) charCode;
            return charValue;
    }

    public String binaryArrayToString(List<String> byteStrings) {
        String outputString = "";
        for (String aByte : byteStrings) {
            char c = this.binaryStringToChar(aByte);
            outputString = outputString + c;
        }
        return outputString;
    }

    public String charToBinaryString(char charVal) {
        int intCode = charVal;
        String binaryString = Integer.toBinaryString(intCode);
            return binaryString;
    }

    public String[] stringtoBinaryArray(char[] charArray) {
        String[] binaryArray = new String[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            binaryArray[i] = this.charToBinaryString(charArray[i]);
        }
        return binaryArray;
    }

    public String fixBinaryStringLength(String str, int len) {
        String fixedString = str;
        if (str.length() < len) {
            int remainingZeros = len - str.length();
            fixedString = "0".repeat(remainingZeros) + str;
        }
        return fixedString;
    }

    public String[] fixBitLength(String[] stringArray, int len) {
        String[] longBinaryArray = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longBinaryArray[i] = this.fixBinaryStringLength(stringArray[i], len);
        }
        return longBinaryArray;
    }

    public String[] fixBitLength(String[] stringArray, int[] len) {
        String[] longBinaryArray = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longBinaryArray[i] = this.fixBinaryStringLength(stringArray[i], len[i]);
        }
        return longBinaryArray;
    }

    public Boolean writeACompressedFile(String bitString, char[] symbols, String[] codeForSymbols, String outputFile) {
        List<String> byteStrings = this.stringToByte(bitString);
        String outputString = this.binaryArrayToString(byteStrings);
        System.out.println("List of bits: "+byteStrings.size());
//        for (int i=0;i<byteStrings.size();i++)
//        System.out.println(byteStrings.get(i));

        List<String> codeArray = Arrays.asList(codeForSymbols);

        String codeArrayLength = "";
        for (String str : codeArray) {
            char tempChar = (char) (str.length() + 40);
            codeArrayLength += tempChar;
        }

        String codeString = this.binaryArrayToString(codeArray);
//System.out.println("codeString:> "+codeString);
        String SymbolString = String.valueOf(symbols);
        //New lines in symbols
        int count = 0;
        for (int i=0;i<SymbolString.length();i++)
            if(SymbolString.charAt(i) == '\n' || SymbolString.charAt(i) == '\r')
                count++;
            System.out.println("Count: "+count);
        //New Lines in output
        int countOut = 0;
        for (int i=0;i<codeString.length();i++)
            if(codeString.charAt(i) == '\n' || codeString.charAt(i) == '\r')
                countOut++;
        System.out.println("Count: "+countOut);

        try {
            fileManagement fileWriter = new fileManagement();
            fileWriter.writeToAFile(outputFile, "" + bitString.length()+"\n"+
                    count + "\n" + //line 1
                    countOut + "\n" + //line 1
                    codeArrayLength + "\n" + //line 2
                    codeString + "\n" + //line 3
                    SymbolString + "\n" + //line 4
                    outputString + "\n"); //line 5
            System.out.println("Code Length: "+codeArrayLength);
            System.out.println("List of codes: "+codeString+"-->"+codeString.length());
            System.out.println("List of symbols: "+SymbolString);
            System.out.println("List of chars: "+outputString+"-->"+outputString.length());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public CompressedContent readCompressedFile(String InputFile) {
        fileManagement fileReader = new fileManagement();
        String fileData = fileReader.readOneFile(InputFile).strip();
        String[] inputArrayArray = fileData.split("\n");
        System.out.println("compLength: "+inputArrayArray.length);
        for(int i=0;i<inputArrayArray.length;i++)
            System.out.println(i+"-->"+inputArrayArray[i]);
        if (inputArrayArray.length == this.numberOfLines) {
            char[] codesLength = inputArrayArray[1].toCharArray();

            char[] symbOne = inputArrayArray[4].toCharArray();///////////////////////////
            char[] symbTwo = inputArrayArray[5].toCharArray();///////////////////////////
            char[] symbols = new char[symbOne.length + symbTwo.length + 1];
            for (int i=0;i<symbOne.length;i++)
                symbols[i] = symbOne[i];
            int j = 0;
            for (int i=symbOne.length;i<symbTwo.length + symbOne.length+1;i++,j++)
                if(i==symbOne.length)
                    symbols[i] = '\n';
                else {
                    symbols[i] = symbTwo[j-1];
                }
            char[] charCodes = inputArrayArray[3].toCharArray();
            System.out.println("number of codes: "+charCodes.length);

            char[] charData = inputArrayArray[6].toCharArray();
            System.out.println("number of data: "+charData.length);
            int[] codesArrayLength = new int[codesLength.length];
            for (int k = 0; k < codesLength.length; k++) {
                codesArrayLength[k] = codesLength[k] - 40;
            }

            String[] codes = this.stringtoBinaryArray(charCodes);
            String[] codeForSymbols = this.fixBitLength(codes, codesArrayLength);//////////////////////

            String[] bitStringArray = this.stringtoBinaryArray(charData);
            bitStringArray = this.fixBitLength(bitStringArray, 8);
            String sum = "";
            for (int i=0;i<bitStringArray.length;i++)
            sum = sum + bitStringArray[i];
           // System.out.println("sum: "+sum);
            List<String> codeArray = Arrays.asList(bitStringArray);
            String codeArrayLength = "";
            for (String str : codeArray) {
                char tempChar = (char) str.length();
                codeArrayLength += tempChar;
            }

            String temp = "";
            for (String str : bitStringArray) temp += str;
            int bigningIndex = temp.length() - Integer.valueOf(inputArrayArray[0]) + 9; //one is added to delete 0 added during compression
            String bitString = temp.substring(bigningIndex);//////////////////////////////////
           // System.out.println("Yeabs code: "+bitString);
            CompressedContent compContent = new CompressedContent(bitString, symbols, codeForSymbols);
            return compContent;
        }
        else if(inputArrayArray.length > this.numberOfLines ){
            //codeLength of each character
            char[] codesLength = inputArrayArray[3].toCharArray();
            //number of binary code words
            int codeLines = Integer.valueOf(inputArrayArray[2]);
            //symbols list
            ArrayList<LinkedList> code  = new ArrayList<LinkedList>();
            char[] codeOne;
            for (int i=0;i<=codeLines;i++) {
                codeOne = inputArrayArray[i + 4].toCharArray();///////////////////////////
               // System.out.println(i+"---->"+codeOne.length);
                code.add(new LinkedList());
                code.get(i).insert(codeOne);
            }
            int codeLength = 0;
            for (int i=0;i<code.size();i++)
                codeLength = codeLength + ((char[]) code.get(i).head.Char).length;
            char[] charCodes = new char[codeLength + codeLines];
            int k = 0;
            for (int i=0;i<code.size();i++) {
                for (int j = 0; j < ((char[]) code.get(i).head.Char).length; j++, k++) {
                    if (i > 0 && k == ((char[]) code.get(i - 1).head.Char).length){
                        charCodes[k] = '\n';
                        k++;
                        charCodes[k] = ((char[]) code.get(i).head.Char)[j];
                    }
                    else {
                        charCodes[k] = ((char[]) code.get(i).head.Char)[j];
                    }
                }
            }
               // System.out.println("-->"+charCodes.length);

            //number of newLines in symbols//////////////////////////////////////////////////////////////////////////////
            int symbLines = Integer.valueOf(inputArrayArray[1]);
            //symbols list
            ArrayList<LinkedList> symb  = new ArrayList<LinkedList>();
            char[] symbOne;
            for (int i=0;i<=symbLines;i++) {
                symbOne = inputArrayArray[i + 5 + codeLines ].toCharArray();///////////////////////////
                symb.add(new LinkedList());
                symb.get(i).insert(symbOne);
            }
            int symbolsLength = 0;
            for (int i=0;i<symb.size();i++)
                symbolsLength = symbolsLength + ((char[]) symb.get(i).head.Char).length;
            char[] symbols = new char[symbolsLength + symbLines];
            k = 0;
            for (int i=0;i<symb.size();i++) {
                for (int j = 0; j < ((char[]) symb.get(i).head.Char).length; j++, k++) {
                    if (i > 0 && k == ((char[]) symb.get(i - 1).head.Char).length){
                        symbols[k] = '\n';
                        k++;
                        symbols[k] = ((char[]) symb.get(i).head.Char)[j];
                }
                    else {
                        symbols[k] = ((char[]) symb.get(i).head.Char)[j];
                    }
                }
            }

            //number of newLines in outPut////////////////////////////////////////////////////////////////////////////////////////
            int outPutLines = inputArrayArray.length - (6 +codeLines+ symbLines) ;
            //output List
            ArrayList<LinkedList> outPut  = new ArrayList<LinkedList>();
            char[] outOne;
            for (int i=0;i<outPutLines;i++) {
                outOne = inputArrayArray[i + symbLines + codeLines + 6].toCharArray();///////////////////////////
                outPut.add(new LinkedList());
                outPut.get(i).insert(outOne);
            }

            int charDataLength = 0;
            for (int i=0;i<outPut.size();i++)
                charDataLength = charDataLength + ((char[]) outPut.get(i).head.Char).length;
            char[] charData = new char[charDataLength + outPutLines];

            k = 0;
            for (int i=0;i<outPut.size();i++) {
                for (int j = 0; j < ((char[]) outPut.get(i).head.Char).length; j++, k++) {
                    if (i > 0 && k == ((char[]) outPut.get(i - 1).head.Char).length){
                        charData[k] = '\n';
                        k++;
                        charData[k] = ((char[]) outPut.get(i).head.Char)[j];
                    }
                    else {
                        charData[k] = ((char[]) outPut.get(i).head.Char)[j];
                    }
                }
            }


            int[] codesArrayLength = new int[codesLength.length];
            for (k = 0; k < codesLength.length; k++) {
                codesArrayLength[k] = codesLength[k] - 40;
            }
           // System.out.println("codes: "+charCodes.length+"-----"+"codeSymbols: "+codesArrayLength.length);

            String[] codes = this.stringtoBinaryArray(charCodes);
            String[] codeForSymbols = this.fixBitLength(codes, codesArrayLength);//////////////////////
            String[] bitStringArray = this.stringtoBinaryArray(charData);
            bitStringArray = this.fixBitLength(bitStringArray, 8);

            List<String> codeArray = Arrays.asList(bitStringArray);
            String codeArrayLength = "";
            for (String str : codeArray) {
                char tempChar = (char) str.length();
                codeArrayLength += tempChar;
            }

            String temp = "";
            for (String str : bitStringArray) temp += str;
            int bigningIndex = temp.length() - Integer.valueOf(inputArrayArray[0]) + 1; //one is added to delete 0 added during compression
            String bitString = temp.substring(bigningIndex);//////////////////////////////////

            CompressedContent compContent = new CompressedContent(bitString, symbols, codeForSymbols);
            return compContent;
        }
        else {
            System.out.println("file not properly written");
            return null;
        }
    }


}

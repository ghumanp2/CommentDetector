package com.company;

//Author: Purnoor Singh Ghuman

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class fileReader{

    public static void readFile(String pathname, String singleComm, String multiCommStart, String multiCommEnd)
            throws IOException {
        File file = new File(pathname);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String codeLine;

        int lines = 0;
        int singleComments = 0;
        int blockComments = 0;
        boolean inBlock = false;
        int blocks = 0;
        int TODO = 0;
        while ((codeLine = buffer.readLine()) != null){
            lines++;

            codeLine = codeLine.replace("\""+ singleComm +"\"", "");
            codeLine = codeLine.replace("\""+ multiCommStart +"\"", "");
            codeLine = codeLine.replace("\""+ multiCommEnd +"\"", "");

            if (inBlock){
                blockComments++;

                //checking to see if block ends in this line
                int blockEndIndex = codeLine.indexOf(multiCommEnd);
                if (blockEndIndex >= 0) {
                    inBlock = false;
                    String codeLineStart = codeLine.substring(0, blockEndIndex);
                    codeLine = codeLine.substring(blockEndIndex + 1);
                    //TO DO has to be before comment end to be valid
                    if (codeLineStart.contains("TODO")){
                        TODO ++;
                    }
                }
                else{
                    if (codeLine.contains("TODO"))
                        TODO++;
                }
            }

            //either the block comment ended or we're checking a new line
            if (!inBlock){
                int blockIndex = codeLine.indexOf(multiCommStart);
                int singleIndex = codeLine.indexOf(singleComm);

                //no comments of any type in this line
                if (singleIndex == -1 && blockIndex == -1) {
                }

                //there's only a single comment in this line
                else if (blockIndex == -1) {
                    singleComments++;
                    codeLine = codeLine.substring(singleIndex + 1);
                    if (codeLine.contains("TODO"))
                        TODO++;

                //there's only block comments in this line (might be multiple block comments - case is checked)
                } else if (singleIndex == -1) {
                    while (codeLine.contains(multiCommStart) && !inBlock) {
                        blocks++;
                        blockComments++;
                        inBlock = true;

                        //codeline starts from the block we are looking at
                        blockIndex = codeLine.indexOf(multiCommStart);
                        codeLine = codeLine.substring(blockIndex + 1);

                        //if there is a block end then there might be more blocks
                        if (codeLine.contains(multiCommEnd)) {
                            int blockEndIndex = codeLine.indexOf(multiCommEnd);
                            String codeLineStart = codeLine.substring(0, blockEndIndex);
                            codeLine = codeLine.substring(blockEndIndex + 1);

                            if (codeLineStart.contains("TODO")) //check if there is a to do in the comment
                                TODO ++;

                            inBlock = false;
                        }
                        else{
                            if (codeLine.contains("TODO"))
                                TODO++;
                        }
                    }
                }

                //code has both single comment identifiers and block comment identifiers - have to check cases
                // of comments within comments
                else {
                    boolean singleLine = false; //used to identify when a single comment has started

                    //while there are more comment identifiers and we are not in a comment already
                    while ((!singleLine && !inBlock && codeLine.contains(multiCommStart)) ||
                            (!singleLine && !inBlock && codeLine.contains(singleComm))){

                        singleIndex = codeLine.indexOf(singleComm);
                        blockIndex = codeLine.indexOf(multiCommStart);

                        //if there is a single comment before block comment
                        if (singleIndex < blockIndex || (singleIndex >= 0 && blockIndex == -1)){
                            codeLine = codeLine.substring(singleIndex+1);
                            if (codeLine.contains("TODO"))
                                TODO++;

                            singleLine = true;
                            singleComments++;
                        }

                        //if there is a block comment before single comment
                        else if (singleIndex > blockIndex || (blockIndex >= 0 && singleIndex == -1)){
                            blocks++;
                            blockComments++;

                            inBlock = true;
                            blockIndex = codeLine.indexOf(multiCommStart);
                            codeLine = codeLine.substring(blockIndex + 1);

                            if (codeLine.contains(multiCommEnd)) { //if the block ends in the same line
                                int blockEndIndex = codeLine.indexOf(multiCommEnd);
                                String codeLineStart = codeLine.substring(0, blockEndIndex);
                                codeLine = codeLine.substring(blockEndIndex + 1);
                                inBlock = false;

                                if (codeLineStart.contains("TODO"))
                                    TODO ++;
                            }
                            else{
                                if (codeLine.contains("TODO"))
                                    TODO++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Total # of lines: " + lines);
        System.out.println("Total # of comment lines: " + (singleComments + blockComments));
        System.out.println("Total # of single line comments: " + singleComments);
        System.out.println("Total # of comment lines within block comments: " + blockComments);
        System.out.println("Total # of block line comments: " + blocks);
        System.out.println("Total # of TODO's: " + TODO);
    }
}

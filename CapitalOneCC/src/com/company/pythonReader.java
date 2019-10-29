package com.company;
//Author: Purnoor Singh Ghuman

import java.io.*;

public class pythonReader {
    public static void readFile(String pathname) throws IOException {
        File file = new File(pathname);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String codeLine;

        //counts keeping track of different type of comments in the code
        int lines = 0;
        int singleComments = 0;
        int blockComments = 0;
        boolean inBlock = false;
        int blocks = 0;
        int TODO = 0;
        int blockTemp = 0;

        while ((codeLine = buffer.readLine()) != null){
            lines++;

            if (inBlock) {
                codeLine = codeLine.trim();
                int commentIndex = codeLine.indexOf("#");
                if (commentIndex == 0) { //this is a consecutive comment line
                    blockTemp++;
                    if (codeLine.contains("TODO")){
                        TODO++;
                    }
                }
                else {
                    inBlock = false;
                    if(blockTemp == 1) { //the past line was a single comment
                        singleComments++;
                        blocks--;
                        blockTemp = 0;
                    }
                    else { //the block ended
                        blockComments += blockTemp;
                        blockTemp = 0;
                    }
                }
            }
            if (!inBlock) {
                int commentIndex = codeLine.indexOf("#");
                if (commentIndex >= 0){
                    codeLine = codeLine.substring(commentIndex + 1);
                    if (codeLine.contains("TODO")){
                        TODO++;
                    }
                    //we will determine in the next line in the program code whether it is a block actually or not
                    inBlock = true;
                    blockTemp = 1;
                    blocks++;
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
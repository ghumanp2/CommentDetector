package com.company;
//Author: Purnoor Singh Ghuman

import java.io.*;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws IOException {

        //getting input of the file that the user wants to examine
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the path of the file you want to examine: ");
        String filePath = scanner.nextLine();

        //extracting the extension of the file
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i+1);
        }
        extension = extension.toLowerCase();

        String singleLineComment = "";
        String blockCommentStart = "";
        String blockCommentEnd = "";

       // System.out.println(extension);

        switch (extension) {
            //C, C++, Java, JavaScript extensions
            case "c":
            case "cpp":
            case "cc":
            case "h":
            case "java":
            case "class":
            case "js":
                System.out.println("here");
                singleLineComment = "//";
                blockCommentStart = "/*";
                blockCommentEnd = "*/";
                fileReader.readFile(filePath, singleLineComment, blockCommentStart, blockCommentEnd);
                break;
            //Ruby
            case "rb":
            case "ruby":
                singleLineComment = "//";
                blockCommentStart = "=begin";
                blockCommentEnd = "=end";
                fileReader.readFile(filePath, singleLineComment, blockCommentStart, blockCommentEnd);
                break;
            //Python (special case - python doesn't have a well defined block comment technique)
            case "py":
                pythonReader.readFile(filePath);
                break;
        }
        return;
    }
}
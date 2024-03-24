package edu.school21.printer.app;

import edu.school21.printer.logic.BMPPrinter;

public class Program {
    public static void main(String[] args) {
        if (args.length != 3) {
            printError("Incorrect count of arguments");
        }
        String filePath = args[0];
        if (args[1].length() != 1 || args[2].length() != 1) {
            printError("Wrong color argument");
        }
        char white = args[1].charAt(0);
        char black = args[2].charAt(0);
        BMPPrinter printer = new BMPPrinter();
        printer.convert(filePath, white, black);
        printer.printCharArray();
    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

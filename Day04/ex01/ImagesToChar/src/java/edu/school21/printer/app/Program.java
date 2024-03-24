package edu.school21.printer.app;

import edu.school21.printer.logic.BMPPrinter;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            printError("Incorrect count of arguments");
        }
        if (args[0].length() != 1 || args[1].length() != 1) {
            printError("Wrong color argument");
        }
        char white = args[0].charAt(0);
        char black = args[1].charAt(0);
        BMPPrinter printer = new BMPPrinter();
        printer.convert(white, black);
        printer.printCharArray();
    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

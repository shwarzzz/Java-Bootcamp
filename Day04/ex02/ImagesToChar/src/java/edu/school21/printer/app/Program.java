package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.BMPPrinter;
import edu.school21.printer.logic.CommanderArguments;

public class Program {
    public static void main(String[] args) {
        CommanderArguments commanderArguments = new CommanderArguments();
        try {
            JCommander.newBuilder()
                    .addObject(commanderArguments)
                    .build()
                    .parse(args);
            BMPPrinter printer = new BMPPrinter();
            printer.convert(commanderArguments.white, commanderArguments.black);
            printer.printCharArray();
        } catch (ParameterException e) {
            printError("Wrong arguments!");
        }
    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

package ex02;

import java.util.Scanner;

public class ConsoleApplication {
    private final String CD = "cd";
    private final String LS = "ls";
    private final String MV = "mv";
    private final String END = "exit";
    private Commander commander;

    public ConsoleApplication() {

    }

    public void startEvenLoop(String startPath) {
        validateStartPath(startPath);
        Scanner inp = new Scanner(System.in);
        String line;
        System.out.println(commander.getPath());
        while (true) {
            line = inp.nextLine();
            String[] parsedLine = line.split("\\s+");
            if (parsedLine.length == 2 && parsedLine[0].equals(CD)) {
                cd(parsedLine);
            } else if (parsedLine.length == 1 && parsedLine[0].equals(LS)) {
                commander.showListDirectoryContent();
            } else if (parsedLine.length == 3 && parsedLine[0].equals(MV)) {
                mv(parsedLine);
            } else if (parsedLine.length == 1 && parsedLine[0].equals(END)) {
                inp.close();
                break;
            } else {
                System.out.println("Wrong command");
            }
        }
    }

    private void validateStartPath(String startPath) {
        try {
            commander = new Commander(startPath);
        } catch (IllegalArgumentException e) {
            System.out.println("Can`t set " + startPath +
                    " as a start directory. Bye!");
            System.exit(-1);
        }
    }

    private void cd(String[] arguments) {
        try {
            commander.changeDirectory(arguments[1]);
            System.out.println(commander.getPath());
        } catch (IllegalArgumentException e) {
            System.out.println("Can't change directory to " + arguments[1]);
        }
    }

    private void mv(String[] arguments) {
        commander.moveFile(arguments[1], arguments[2]);
    }
}

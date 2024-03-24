package ex03;

import java.util.Scanner;

public class Program {
    static final String WEEK_TEMPLATE = "Week ";
    static final String END_OF_SEQUENCE = "42";
    static final int NUMBERS_OF_TESTS = 5;
    static final int MAX_WEEKS_NUMBER = 18;

    public static void main(String[] args) {
        long grades = 0;
        Scanner inp = new Scanner(System.in);
        int weeksCount = 0;
        while (inp.hasNextLine()) {
            String tmp = inp.nextLine();
            if (tmp.equals(END_OF_SEQUENCE)) {
                break;
            }
            weeksCount++;
            if (weeksCount > MAX_WEEKS_NUMBER) {
                printError(inp);
            }
            if (!tmp.equals(WEEK_TEMPLATE + weeksCount)) {
                printError(inp);
            }

            grades += findMinimalGrade(inp) * calculatePower(10, weeksCount - 1);
        }
        inp.close();
        printResult(grades);
    }

    public static void printError(Scanner inp) {
        inp.close();
        System.out.println("Illegal Argument");
        System.exit(-1);
    }

    public static int findMinimalGrade(Scanner inp) {
        int min = 9;
        for (int i = 0; i < NUMBERS_OF_TESTS; i++) {
            int tmp = inp.nextInt();
            if (tmp < 1 || tmp > 9) {
                printError(inp);
            }
            min = tmp < min ? tmp : min;
        }
        inp.nextLine();
        return min;
    }

    public static long calculatePower(int number, int power) {
        long result = 1;
        for (int i = 0; i < power; i++) {
            result *= number;
        }
        return result;
    }

    public static void printResult(long grades) {
        for (int i = 0; grades != 0; i++) {
            System.out.printf("%s%d ", WEEK_TEMPLATE, i + 1);
            long grade = grades % 10;
            for (int j = 0; j < grade; j++) {
                System.out.print('=');
            }
            System.out.println('>');
            grades /= 10;
        }
    }

}
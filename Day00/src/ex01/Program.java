package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int number = inp.nextInt();
        inp.close();
        if (number <= 1) {
            printError(inp);
        }
        int count = 0;
        int max = sqrt(number);
        int devisor = 2;
        boolean isPrime = true;
        while (devisor <= max) {
            count++;
            if (number % devisor == 0) {
                isPrime = false;
                break;
            }
            devisor++;
        }
        System.out.println(isPrime + " " + count);
    }

    public static int sqrt(int number) {
        int result = 1;
        while (result * result < number) {
            result++;
        }
        return result;
    }

    public static void printError(Scanner inp) {
        inp.close();
        System.out.println("Illegal Argument");
        System.exit(-1);
    }
}

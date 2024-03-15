package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int result = 0;
        int number;
        while ((number = inp.nextInt()) != 42) {
            int tmp = calculateDigitsSum(number);
            result += isPrime(tmp) ? 1 : 0;
        }
        System.out.println("Count of coffee-request - " + result);
        inp.close();
    }

    public static int calculateDigitsSum(int number) {
        int sum = 0;
        if (number > 1) {
            while (number != 0) {
                sum += number % 10;
                number /= 10;
            }
        }
        return sum;
    }

    public static boolean isPrime(int number) {
        int max = sqrt(number);
        int devisor = 2;
        if (number < 2) {
            return false;
        }
        while (devisor <= max) {
            if (number % devisor == 0) {
                return false;
            }
            devisor++;
        }
        return true;
    }

    public static int sqrt(int number) {
        int result = 1;
        while (result * result < number) {
            result++;
        }
        return result;
    }
}

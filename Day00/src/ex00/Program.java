package ex00;

import java.util.Scanner;

public class Program {
    static final int MIN_SIX_DIGiT_NUMBER = 100000;
    static final int MAX_SIX_DIGIT_NUMBER = 999999;

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int number = inp.nextInt();
        inp.close();
        number *= number < 0 ? -1 : 1;
        if (number < MIN_SIX_DIGiT_NUMBER || number > MAX_SIX_DIGIT_NUMBER) {
            System.out.println("Illegal argument");
            System.exit(-1);
        }
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        System.out.println(sum);
    }
}

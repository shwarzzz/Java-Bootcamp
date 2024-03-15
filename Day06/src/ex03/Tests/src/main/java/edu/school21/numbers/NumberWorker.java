package edu.school21.numbers;

import static java.lang.Math.sqrt;

public class NumberWorker {
    public NumberWorker() {

    }

    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Number must be > 1");
        }
        for (int divisor = 2; divisor <= Math.floor(sqrt(number)); divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitSum(int number) {
        number *= number < 0 ? -1 : 1;
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static class IllegalNumberException extends RuntimeException {
        public IllegalNumberException(String message) {
            super(message);
        }
    }
}

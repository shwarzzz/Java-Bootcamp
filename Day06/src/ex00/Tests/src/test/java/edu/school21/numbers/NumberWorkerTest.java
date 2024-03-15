package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest(name = "Test {index} - {0} is prime")
    @ValueSource(ints = {7, 2, 3, 3463, 2081, 73, 17, 13, 1949, 5})
    void isPrimeForPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest(name = "Test {index} - {0} is not prime")
    @ValueSource(ints = {4, 77, 100, 3721, 12, 88, 1234, 15, 169, 9})
    void isPrimeForNotPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest(name = "Test {index} - {0} incorrect")
    @ValueSource(ints = {-1, 0, 1, -125, -4, -10000, -23, -2, -4, -10})
    void isPrimeForIncorrectNumbers(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(NumberWorker.IllegalNumberException.class, () -> {
            numberWorker.isPrime(number);
        });
    }

    @ParameterizedTest(name = "Test {index} - sum of {0} digits = {1}")
    @CsvFileSource(resources = "/data.csv")
    void digitSumFromCsv(int digits, int checkSum) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(checkSum, numberWorker.digitSum(digits));
    }
}

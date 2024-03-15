package ex02;

import java.util.Random;

public class ArrayGenerator {
    private final int MAX_VALUE = 1000;
    private final int[] array;
    private long sum;

    public ArrayGenerator(int arraySize) {
        array = new int[arraySize];
        fillArray();
    }

    private void fillArray() {
        Random randomizer = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = randomizer.nextInt() % (MAX_VALUE + 1);
            sum += array[i];
        }
    }

    public int[] getArray() {
        return array;
    }

    public long getSum() {
        return sum;
    }
}

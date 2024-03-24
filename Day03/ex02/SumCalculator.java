package ex02;

public class SumCalculator implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;

    private SharedVariable sum;

    public SumCalculator(int[] array, int start, int end, SharedVariable sum) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.sum = sum;
    }

    @Override
    public void run() {
        long tmpSum = 0;
        for (int i = start; i <= end; i++) {
            tmpSum += array[i];
        }
        printSumInfo(tmpSum);
        sum.add(tmpSum);
    }

    private void printSumInfo(long sumValue) {
        System.out.println(Thread.currentThread().getName() + ": from " +
                start + " to " + end + " sum is " + sumValue);
    }
}

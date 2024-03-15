package ex02;

import java.util.ArrayList;
import java.util.List;

public class ThreadsSumCalculator {
    private final List<Thread> myThreads;
    private final int threadsCount;

    private final SharedVariable result = new SharedVariable();

    public ThreadsSumCalculator(int threadsCount) {
        this.threadsCount = threadsCount;
        myThreads = new ArrayList<>();
    }

    public long calculateSum(int[] array) {
        createThreads(array);
        for (Thread t : myThreads) {
            t.start();
        }
        for (Thread t : myThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        return result.getSum();
    }

    private void createThreads(int[] array) {
        boolean isUniform = array.length % threadsCount == 0;
        int elementsCount = array.length / threadsCount;
        elementsCount += !isUniform ? 1 : 0;
        for (int i = 0; i < threadsCount - 1; i++) {
            myThreads.add(new Thread(new SumCalculator(array,
                    i * elementsCount,
                    (i + 1) * elementsCount - 1, result),
                    "Thread " + (i + 1)));
        }
        myThreads.add(new Thread(new SumCalculator(array,
                (threadsCount - 1) * elementsCount,
                array.length - 1, result),
                "Thread " + threadsCount));
    }
}

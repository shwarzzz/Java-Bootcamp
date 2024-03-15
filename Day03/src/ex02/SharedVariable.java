package ex02;

public class SharedVariable {
    private long sum;

    public SharedVariable() {
    }

    public synchronized void add(long value) {
        sum += value;
    }

    public long getSum() {
        return sum;
    }
}

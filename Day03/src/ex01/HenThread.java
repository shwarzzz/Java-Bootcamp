package ex01;

public class HenThread extends Thread {
    private final int numberOfIterations;
    private final Object monitor;

    private WaitChecker checker;

    public HenThread(int numberOfIterations, Object monitor, WaitChecker checker) {
        this.numberOfIterations = numberOfIterations;
        this.monitor = monitor;
        this.checker = checker;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            synchronized (monitor) {
                try {
                    while (checker.getStatus()) {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Hen");
                checker.setStatus(true);
                monitor.notify();
            }
        }
    }
}

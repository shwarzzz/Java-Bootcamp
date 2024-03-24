package ex00;

public class HenThread extends Thread {
    private final int numberOfIterations;

    public HenThread(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            System.out.println("Hen");
        }
    }
}

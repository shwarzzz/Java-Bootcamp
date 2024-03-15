package ex00;

public class EggThread extends Thread {
    private final int numberOfIterations;

    public EggThread(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            System.out.println("Egg");
        }
    }
}

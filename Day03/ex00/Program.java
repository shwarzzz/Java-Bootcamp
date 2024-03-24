package ex00;

public class Program {
    public static final String COUNT_FLAG = "--count";

    public static final String HUMAN = "Human";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Incorrect arguments count");
            System.exit(-1);
        }
        String[] data = args[0].split("=");
        if (data.length != 2 || !data[0].equals(COUNT_FLAG)) {
            System.out.println("Incorrect argument");
            System.exit(-1);
        }
        try {
            Integer numberOfIterations = Integer.parseInt(data[1]);
            if (numberOfIterations <= 0) {
                throw new IllegalArgumentException("Iterations count " +
                        "must be > 0");
            }
            EggThread eggThread = new EggThread(numberOfIterations);
            HenThread henThread = new HenThread(numberOfIterations);
            eggThread.start();
            henThread.start();
            eggThread.join();
            henThread.join();
            for (int i = 0; i < numberOfIterations; i++) {
                System.out.println(HUMAN);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

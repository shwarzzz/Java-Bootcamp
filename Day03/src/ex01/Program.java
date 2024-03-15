package ex01;

public class Program {
    public static final String COUNT_FLAG = "--count";

    public static final Object MONITOR = new Object();

    public static WaitChecker CHECKER = new WaitChecker(true);


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
            EggThread eggThread = new EggThread(numberOfIterations, MONITOR, CHECKER);
            HenThread henThread = new HenThread(numberOfIterations, MONITOR, CHECKER);
            henThread.start();
            eggThread.start();
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

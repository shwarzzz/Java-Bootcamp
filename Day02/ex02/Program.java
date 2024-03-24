package ex02;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong parameters count!");
            System.exit(-1);
        }
        ConsoleApplication app = new ConsoleApplication();
        app.startEvenLoop(args[0]);
    }
}

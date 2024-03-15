package ex02;

public class Program {
    private static final String SIZE_FLAG = "--arraySize";
    private static final String THREADS_FLAG = "--threadsCount";

    private static final int MAX_ARRAY_SIZE = 2000000;

    public static void main(String[] args) {
        if (args.length != 2) {
            ArgumentsValidator.printError("Incorrect arguments count!");
        }
        int arraySize = ArgumentsValidator.validate(args[0], SIZE_FLAG);
        int threadsCount = ArgumentsValidator.validate(args[1], THREADS_FLAG);
        if (arraySize < threadsCount) {
            ArgumentsValidator.printError("Array size must " +
                    "be >= threads count");
        }
        if (arraySize > MAX_ARRAY_SIZE) {
            ArgumentsValidator.printError("Array size must be <= "
                    + MAX_ARRAY_SIZE);
        }
        ArrayGenerator generator = new ArrayGenerator(arraySize);
        System.out.println("Sum: " + generator.getSum());
        ThreadsSumCalculator calculator = new ThreadsSumCalculator(threadsCount);
        System.out.println("Sum by threads: " +
                calculator.calculateSum(generator.getArray()));
    }
}

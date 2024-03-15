package ex03;

public class ArgumentsValidator {
    private static final String ERROR_TEMPLATE = "Incorrect flag ";

    public static int validate(String arg, String flag) {
        int result = 0;
        String[] flagData = arg.split("=");
        if (flagData.length != 2 || !flagData[0].equals(flag)) {
            printError(ERROR_TEMPLATE + arg);
        }
        try {
            result = Integer.parseInt(flagData[1]);
        } catch (NumberFormatException e) {
            printError(ERROR_TEMPLATE + flag + " value");
        }
        if (result <= 0) {
            printError(ERROR_TEMPLATE + flag + " value");
        }
        return result;
    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

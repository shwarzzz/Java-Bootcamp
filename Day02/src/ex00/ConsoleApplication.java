package ex00;

import java.util.Scanner;

public class ConsoleApplication {

    public ConsoleApplication() {
    }

    public void startEventLoop() {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter the path to signature.txt file:");
        SignatureChecker checker =
                new SignatureChecker(SignaturesFileParser.getInstance().
                        getParsedSignatures(inp.nextLine()));
        SignatureToFileWriter writer = new SignatureToFileWriter();
        while (true) {
            System.out.println("Enter path to file:");
            String path = inp.nextLine();
            if (path.equals("42")) {
                System.out.println("Bye!");
                inp.close();
                break;
            }
            try {
                String signature = checker.getFileSignature(path);
                writer.writeToFile(signature);
            } catch (UndefinedSignatureException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

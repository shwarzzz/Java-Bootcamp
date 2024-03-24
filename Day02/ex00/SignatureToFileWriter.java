package ex00;

import java.io.FileOutputStream;
import java.io.IOException;

public class SignatureToFileWriter {
    private static final String RESULT_FILE = "result.txt";

    public SignatureToFileWriter() {
    }

    public void writeToFile(String signature) {
        try (FileOutputStream out = new FileOutputStream(RESULT_FILE, true)) {
            for (char c : signature.toCharArray()) {
                out.write(c);
            }
            out.write('\n');
        } catch (IOException e) {
            System.exit(-1);
        }
    }
}

package ex00;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignatureChecker {
    private final Signatures signatures;

    public SignatureChecker(Signatures signatures) {
        this.signatures = signatures;
    }

    public String getFileSignature(String filePath) {
        try (FileInputStream inp = new FileInputStream(filePath)) {
            List<Integer> fileSignature = readFileSignature(inp);
            for (Map.Entry<String, List<Integer>> entry :
                    signatures.getSignatures().entrySet()) {
                List<Integer> signature = entry.getValue();
                if (isEqual(signature, fileSignature)) {
                    System.out.println("PROCESSED");
                    return entry.getKey();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        throw new UndefinedSignatureException("UNDEFINED");
    }

    private List<Integer> readFileSignature(FileInputStream input) throws IOException {
        int symbol;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < signatures.getMaxLength(); i++) {
            symbol = input.read();
            if (symbol == SignaturesFileParser.EOF) {
                break;
            }
            result.add(symbol);
        }
        return result;
    }

    private boolean isEqual(List<Integer> first, List<Integer> second) {
        if (first.size() > second.size()) {
            return false;
        }
        for (int i = 0; i < first.size(); i++) {
            if (!first.get(i).equals(second.get(i))) {
                return false;
            }
        }
        return true;
    }
}

package ex00;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignaturesFileParser {
    public static final int EOF = -1;
    private static SignaturesFileParser instance;

    private SignaturesFileParser() {

    }

    public static SignaturesFileParser getInstance() {
        if (instance == null) {
            instance = new SignaturesFileParser();
        }
        return instance;
    }

    public Signatures getParsedSignatures(String filePath) {
        Map<String, List<Integer>> parsedSignatures = new HashMap<>();
        int maxLength = 0;
        try (FileInputStream inp = new FileInputStream(filePath)) {
            int symbol;
            StringBuilder line = new StringBuilder();
            do {
                symbol = inp.read();
                if (symbol == '\n' || symbol == EOF) {
                    String[] data = line.toString().split(", ");
                    line.setLength(0);
                    if (data.length != 2) {
                        throw new IllegalSignatureFormatException("Illegal " +
                                "signature format");
                    }
                    String[] magicNumbers = data[1].split(" ");
                    List<Integer> numbers = getMagicNumberList(magicNumbers);
                    maxLength = Math.max(maxLength, numbers.size());
                    parsedSignatures.put(data[0], numbers);
                } else {
                    line.append((char) symbol);
                }
            } while (symbol != EOF);
        } catch (IOException | IllegalSignatureFormatException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return new Signatures(parsedSignatures, maxLength);
    }

    private List<Integer> getMagicNumberList(String[] data) {
        List<Integer> result = new ArrayList<>();
        for (String str : data) {
            if (str.length() != 2) {
                throw new IllegalSignatureFormatException("Illegal " +
                        "signature format");
            }
            result.add(Integer.parseInt(str, 16));
        }
        return result;
    }
}

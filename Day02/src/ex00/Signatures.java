package ex00;


import java.util.List;
import java.util.Map;

public class Signatures {
    private final Map<String, List<Integer>> currentSignatures;
    private final int maxLength;

    public Signatures(Map<String, List<Integer>> signatures, int maxLength) {
        currentSignatures = signatures;
        this.maxLength = maxLength;
    }

    public Map<String, List<Integer>> getSignatures() {
        return currentSignatures;
    }

    public int getMaxLength() {
        return maxLength;
    }
}

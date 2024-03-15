package ex00;

public class IllegalSignatureFormatException extends RuntimeException {
    public IllegalSignatureFormatException() {
        super();
    }

    public IllegalSignatureFormatException(String message) {
        super(message);
    }
}

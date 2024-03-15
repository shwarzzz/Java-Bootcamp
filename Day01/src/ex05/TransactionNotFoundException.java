package ex05;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super();
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}

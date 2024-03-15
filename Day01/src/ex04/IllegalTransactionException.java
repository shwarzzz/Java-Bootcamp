package ex04;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException() {
        super();
    }

    public IllegalTransactionException(String message) {
        super(message);
    }
}

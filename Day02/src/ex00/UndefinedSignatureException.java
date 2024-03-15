package ex00;

public class UndefinedSignatureException extends RuntimeException {
    public UndefinedSignatureException() {
        super();
    }

    public UndefinedSignatureException(String mesage) {
        super(mesage);
    }
}

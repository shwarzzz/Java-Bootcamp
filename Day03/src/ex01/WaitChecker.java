package ex01;

public class WaitChecker {
    private boolean isWait;

    public WaitChecker(boolean isWait) {
        this.isWait = isWait;
    }

    public boolean getStatus() {
        return isWait;
    }

    public void setStatus(boolean value) {
        isWait = value;
    }
}

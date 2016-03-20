package exceptions;

public class InexchangeableFloorsException extends Exception {
    String message;

    public InexchangeableFloorsException() {}
    public InexchangeableFloorsException(String message) {
        this.message = message;
    }

    public String toString() {
        return "Exception InexchangeableFloorsException: " + message;
    }
}

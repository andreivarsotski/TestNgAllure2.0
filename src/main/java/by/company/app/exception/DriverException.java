package by.company.app.exception;

public class DriverException extends RuntimeException {

    public DriverException() {
        super("Problem with driver initializing. See below for details: ");
    }

    public DriverException(String message) {
        super(message);
    }
}

package bookingService.exception;

public class UnsupportedStateException extends RuntimeException {

    public UnsupportedStateException(String message) {
        super(message);
    }

}
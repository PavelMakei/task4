package by.makei.shop.exception;

public class DbPoolException extends Exception{
    public DbPoolException() {
    }

    public DbPoolException(String message) {
        super(message);
    }

    public DbPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbPoolException(Throwable cause) {
        super(cause);
    }
}

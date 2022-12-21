package appLibrary.exceptions;

public class MatchException extends RuntimeException{
    public MatchException() {
    }

    public MatchException(String message) {
        super(message);
    }
}

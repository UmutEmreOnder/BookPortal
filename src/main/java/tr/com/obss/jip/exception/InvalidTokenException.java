package tr.com.obss.jip.exception;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String token) {
        super(String.format("The token %s doesn't exist or expired!", token));
    }
}

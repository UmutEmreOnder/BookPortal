package tr.com.obss.jip.exception;

public class AuthorAlreadyExistException extends BaseException {
    public AuthorAlreadyExistException(String username) {
        super(String.format("%s already exist", username));
    }
}

package tr.com.obss.jip.exception;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException(String username) {
        super(String.format("%s already exist", username));
    }
}

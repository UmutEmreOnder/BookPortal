package tr.com.obss.jip.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String username) {
        super(String.format("%s is not exist", username));
    }

    public UserNotFoundException(Long id) {
        super(String.format("User with id %d is not exist", id));
    }
}

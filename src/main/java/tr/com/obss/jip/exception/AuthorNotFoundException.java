package tr.com.obss.jip.exception;

public class AuthorNotFoundException extends BaseException {
    public AuthorNotFoundException() {
    }

    public AuthorNotFoundException(String username) {
        super(String.format("%s is not exist", username));
    }

    public AuthorNotFoundException(Long id) {
        super(String.format("Author with id %d is not exist", id));
    }
}

package tr.com.obss.jip.exception;

public class RequestNotFoundException extends BaseException {
    public RequestNotFoundException() {
    }

    public RequestNotFoundException(String username) {
        super(String.format("%s is not exist", username));
    }

    public RequestNotFoundException(Long id) {
        super(String.format("AddingBookRequest with id %d is not exist", id));
    }
}

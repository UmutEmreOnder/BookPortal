package tr.com.obss.jip.exception;

public class BookAlreadyExistException extends BaseException {
    public BookAlreadyExistException(String name) {
        super(String.format("%s already exist", name));
    }
}

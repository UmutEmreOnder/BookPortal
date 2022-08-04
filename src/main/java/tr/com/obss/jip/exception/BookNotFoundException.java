package tr.com.obss.jip.exception;

public class BookNotFoundException extends BaseException {
    public BookNotFoundException(Long id) {
        super(String.format("The book with id %d is not exist", id));
    }

    public BookNotFoundException(String name) {
        super(String.format("The book with name %s is not exist", name));
    }
}

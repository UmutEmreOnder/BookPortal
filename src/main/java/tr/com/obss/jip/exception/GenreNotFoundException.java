package tr.com.obss.jip.exception;

public class GenreNotFoundException extends BaseException {
    public GenreNotFoundException() {
    }

    public GenreNotFoundException(String type) {
        super(String.format("%s is not exist", type));
    }
}

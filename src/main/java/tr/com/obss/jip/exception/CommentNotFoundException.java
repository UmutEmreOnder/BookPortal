package tr.com.obss.jip.exception;

public class CommentNotFoundException extends BaseException {
    public CommentNotFoundException(Long id) {
        super(String.format("The comment with id %d is not exist", id));
    }
}

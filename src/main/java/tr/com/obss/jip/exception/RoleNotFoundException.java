package tr.com.obss.jip.exception;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String role) {
        super(String.format("%s is not exist", role));
    }
}

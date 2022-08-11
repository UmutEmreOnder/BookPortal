package tr.com.obss.jip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tr.com.obss.jip.dto.ErrorDto;

import javax.servlet.http.HttpServletRequest;

@ResponseBody
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorDto handleException(BaseException ex, HttpServletRequest request) {
        return new ErrorDto(ex.getMessage(), 1000L, request.getRequestURL().toString());
    }
}

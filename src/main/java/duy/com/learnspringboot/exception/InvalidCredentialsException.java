package duy.com.learnspringboot.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidCredentialsException extends RuntimeException {
    private ErrorCode errorCode;

    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}

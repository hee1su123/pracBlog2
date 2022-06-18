package spring.pracblog2.error.exception;

import lombok.Getter;
import spring.pracblog2.error.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode code;

    public BusinessException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

}

package spring.pracblog2.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.pracblog2.error.exception.BusinessException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {


    /* @Valid, @Validated 으로 에러 발생 시 발생. @RequestBody, @RequestPart 에서 주로 발생하는 에러 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, response.getStatus());
    }


    /* 지원하지 않는 Http method */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> hadleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e
    ) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, response.getStatus());
    }


    /* Authentication 객체가 필요한 권한을 보유하지 않음 */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException e
    ) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, response.getStatus());
    }


    /* 직접 설정한 예외 처리 */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBuisnessException(
            BusinessException e
    ) {
        ErrorCode code = e.getCode();
        ErrorResponse response = ErrorResponse.of(code);
        return new ResponseEntity<>(response, code.getStatus());
    }


    /* 그 이외의 모든 예외 처리 */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, response.getStatus());
    }

}

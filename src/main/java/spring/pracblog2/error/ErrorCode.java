package spring.pracblog2.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not Allowed Http Method"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Authenticated User Needs Authorization"),

    // Local
    NO_DATA_IN_DB(HttpStatus.INTERNAL_SERVER_ERROR, "Can't Find Data"),
    NOT_AUTHORIZED(HttpStatus.INTERNAL_SERVER_ERROR, "No Authorization"),
    INVALID_LIKES(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Likes Click"),
    ALREADY_USED(HttpStatus.INTERNAL_SERVER_ERROR, "Being Used By Someone"),
    WRONG_DATA(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Data"),
    ;
    //

    private final HttpStatus status;
    private final String message;

}

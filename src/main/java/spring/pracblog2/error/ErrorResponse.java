package spring.pracblog2.error;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private HttpStatus status;

    private ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}

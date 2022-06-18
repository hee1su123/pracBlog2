package spring.pracblog2.dto.request;

import lombok.Getter;

import javax.validation.constraints.*;

/* Done */
@Getter
public class LoginRequestDto {
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+")
    private String email;

    @Size(min = 4, max = 25)
    private String password;
}

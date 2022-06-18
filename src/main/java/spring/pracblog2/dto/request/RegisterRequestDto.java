package spring.pracblog2.dto.request;

import lombok.Getter;

import javax.validation.constraints.*;

/* Done */
@Getter
public class RegisterRequestDto {

    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+")
    private String email;

    @NotNull(message = "이름을 입력하세요")
    @Size(min = 1, max = 10)
    private String name;

    @Size(min = 3, max = 20)
    private String nickname;

    @Size(min = 4, max = 25)
    private String password;

    @NotNull(message = "소개를 쓰세요")
    @Size(min = 0, max = 1000)
    private String introduce;

}

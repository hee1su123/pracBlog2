package spring.pracblog2.dto.request;

import lombok.Getter;

/* Done */
@Getter
public class RegisterRequestDto {

    private String email;
    private String name;
    private String nickname;
    private String password;
    private String intro;

}

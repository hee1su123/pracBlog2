package spring.pracblog2.dto.response;

import lombok.Getter;
import spring.pracblog2.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    String email;
    String name;
    String nickname;
    String introduce;
    LocalDateTime createdAt;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.introduce = user.getIntro();
        this.createdAt = user.getCreatedAt();
    }
}

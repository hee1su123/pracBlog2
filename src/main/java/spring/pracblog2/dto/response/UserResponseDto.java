package spring.pracblog2.dto.response;

import spring.pracblog2.domain.User;

import java.time.LocalDateTime;

public class UserResponseDto {

    public UserResponseDto(User user) {
        String email = user.getEmail();
        String name = user.getName();
        String nickname = user.getNickname();
        String intro = user.getIntro();
        LocalDateTime createdAt = user.getCreatedAt();
    }
}

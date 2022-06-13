package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.pracblog2.domain.User;
import spring.pracblog2.dto.request.LoginRequestDto;
import spring.pracblog2.dto.request.RegisterRequestDto;
import spring.pracblog2.dto.response.UserResponseDto;
import spring.pracblog2.repository.UserRepository;
import spring.pracblog2.security.JwtTokenProvider;
import spring.pracblog2.security.UserDetailsImpl;
import spring.pracblog2.service.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* 회원가입 Done
    * Service 로 위임
    * */
    @PostMapping("/api/register")
    Boolean signUp(
            @RequestBody RegisterRequestDto requestDto
    ) {
        return userService.register(requestDto);
    }

    /* 로그인 Done
    * Service 로 위임
    * */
    @PostMapping("/api/login")
    public String login(
            @RequestBody LoginRequestDto requestDto
    ) {
        return userService.login(requestDto);
    }

    /* 회원 조회 Done
    * userDetails 에서 id 값 찾아서 Service 로 넘김
    *  */
    @GetMapping("/api/user")
    UserResponseDto getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long id = userDetails.getUser().getId();
        return userService.getUser(id);
    }

}

package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.pracblog2.domain.User;
import spring.pracblog2.dto.request.LoginRequestDto;
import spring.pracblog2.dto.request.RegisterRequestDto;
import spring.pracblog2.dto.response.ResponseMessage;
import spring.pracblog2.dto.response.UserResponseDto;
import spring.pracblog2.repository.UserRepository;
import spring.pracblog2.security.JwtTokenProvider;
import spring.pracblog2.security.UserDetailsImpl;
import spring.pracblog2.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* 회원가입 */
    @PostMapping("/api/register")
    ResponseEntity<ResponseMessage> signUp(
            @Valid @RequestBody RegisterRequestDto requestDto
    ) {
        userService.register(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("회원가입 완료"));
    }

    /* 로그인 */
    @PostMapping("/api/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDto requestDto
    ) {
        String message = userService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /* 회원 조회 */
    @GetMapping("/api/user")
    ResponseEntity<UserResponseDto> getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long id = userDetails.getUser().getId();
        UserResponseDto responseDto = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}

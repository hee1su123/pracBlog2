package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.User;
import spring.pracblog2.dto.request.LoginRequestDto;
import spring.pracblog2.dto.request.RegisterRequestDto;
import spring.pracblog2.dto.response.UserResponseDto;
import spring.pracblog2.error.ErrorCode;
import spring.pracblog2.error.exception.BusinessException;
import spring.pracblog2.repository.UserRepository;
import spring.pracblog2.security.JwtTokenProvider;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    /* 회원가입 Done
    * 해당 이메일이 사용된적 있는지 확인
    * role 은 초기값 ROLE_USER 설정
    * 사용가능 시 save 함수로 DB에 저장
    * */
    public void register(RegisterRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        List<String> roles = Collections.singletonList("ROLE_USER");
        User user = new User(requestDto, password, roles);
        String email = user.getEmail();
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new BusinessException("이미 사용중인 e-mail 입니다", ErrorCode.ALREADY_USED);
        }
        userRepository.save(user);
    }


    /* 로그인 Done
    * 이메일 중복확인
    * 비밀번호 일치 확인
    * jwt 생성 후 반환
    * */
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new BusinessException("해당 e-mail 을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new BusinessException("잘못된 비밀번호 입니다", ErrorCode.WRONG_DATA);
        }
        return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
    }


    /* 회원 조회 Done
    * id 값을 받아 DB 에서 해당 id 와 일치하는 회원 정보 반환
    *  */
    public UserResponseDto getUser(Long id) {
        Optional<User> obj = userRepository.findById(id);
        if (obj.isPresent() && Objects.equals(obj.get().getId(), id)) {
            User user = obj.get();
            return new UserResponseDto(user);
        }
        else {
            throw new BusinessException("해당 사용자를 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB);
        }
    }
}
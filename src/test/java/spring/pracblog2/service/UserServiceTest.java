package spring.pracblog2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.pracblog2.domain.User;
import spring.pracblog2.dto.request.LoginRequestDto;
import spring.pracblog2.dto.request.RegisterRequestDto;
import spring.pracblog2.error.exception.BusinessException;
import spring.pracblog2.repository.UserRepository;
import spring.pracblog2.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {

    @Nested
    class 회원가입 {

        @Mock
        PasswordEncoder passwordEncoder;
        @Mock
        JwtTokenProvider jwtTokenProvider;
        @Mock
        UserRepository userRepository;

        @InjectMocks
        UserService userService;

        @Test
        void 정상적_회원가입() {

            // given
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            RegisterRequestDto requestDto = RegisterRequestDto.builder()
                    .email("hesu@email.com")
                    .name("hesu")
                    .nickname("hs")
                    .password("abcdefg123@")
                    .introduce("Hello~")
                    .build();

            given(passwordEncoder.encode(requestDto.getPassword())).willReturn(encoder.encode(requestDto.getPassword()));
            given(userRepository.findByEmail(requestDto.getEmail())).willReturn(Optional.ofNullable(null));
//            willReturn(user).given(userRepository).save(user); --> 오류 생기는 이유 찾아보기

            // when
            userService.register(requestDto);

            // then
            verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
            verify(userRepository, times(1)).findByEmail(requestDto.getEmail());
            verify(userRepository, times(1)).save(any());
        }

        @Test
        void 이메일_사용중() {

            // given
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            RegisterRequestDto requestDto = RegisterRequestDto.builder()
                    .email("hesu@email.com")
                    .name("hesu")
                    .nickname("hs")
                    .password("abcdefg123@")
                    .introduce("Hello~")
                    .build();

            User user = User.builder()
                    .email(requestDto.getEmail())
                    .name(requestDto.getName())
                    .nickname(requestDto.getNickname())
                    .password(encoder.encode(requestDto.getPassword()))
                    .intro(requestDto.getIntroduce())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            given(passwordEncoder.encode(requestDto.getPassword())).willReturn(encoder.encode(requestDto.getPassword()));
            given(userRepository.findByEmail(requestDto.getEmail())).willReturn(Optional.ofNullable(user));

            // when - then
            assertThrows(BusinessException.class,
                    () -> userService.register(requestDto)
            );

        }

    }

    @Nested
    class 로그인 {

        @Mock
        UserRepository userRepository;
        @Mock
        PasswordEncoder passwordEncoder;
        @Mock
        JwtTokenProvider jwtTokenProvider;

        @InjectMocks
        UserService userService;

        @Test
        void 정상_로그인() {

            // given
            LoginRequestDto requestDto = LoginRequestDto.builder()
                    .email("hesu@email.com")
                    .password("abcdefg123@")
                    .build();

            User user = User.builder()
                    .email(requestDto.getEmail())
                    .password(requestDto.getPassword())
                    .name("hesu")
                    .nickname("hs")
                    .intro("Hello~")
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            String secretKey = "pracBlog2";

            Long tokenValidTime = 30 * 60 * 1000L;

            Claims claims = Jwts.claims().setSubject(user.getEmail());
            claims.put("roles", user.getRoles());
            Date now = new Date();
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + tokenValidTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            given(userRepository.findByEmail(requestDto.getEmail())).willReturn(Optional.of(user));
            given(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())).willReturn(true);
            given(jwtTokenProvider.createToken(user.getEmail(), user.getRoles())).willReturn(token);

            // when - then
            assertEquals(token, userService.login(requestDto));

        }

        @Test
        void 없는_이메일() {

            // given
            LoginRequestDto requestDto = LoginRequestDto.builder()
                    .email("hesu@email.com")
                    .password("abcdefg123@")
                    .build();

            given(userRepository.findByEmail(requestDto.getEmail())).willThrow(BusinessException.class);

            // when - then
            assertThrows(BusinessException.class,
                    () -> userService.login(requestDto)
            );

        }

        @Test
        void 비밀번호_불일치() {

            // given
            LoginRequestDto requestDto = LoginRequestDto.builder()
                    .email("hesu@email.com")
                    .password("abcdefg123@")
                    .build();

            User user = User.builder()
                    .email(requestDto.getEmail())
                    .password("abcdefg123")
                    .name("hesu")
                    .nickname("hs")
                    .intro("Hello~")
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            given(userRepository.findByEmail(requestDto.getEmail())).willReturn(Optional.of(user));
            given(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())).willReturn(false);

            // when - then
            assertThrows(BusinessException.class,
                    () -> userService.login(requestDto)
            );

        }

    }

    @Nested
    class 회원_정보_조회 {

        @Mock
        PasswordEncoder passwordEncoder;
        @Mock
        JwtTokenProvider jwtTokenProvider;
        @Mock
        UserRepository userRepository;

        @InjectMocks
        UserService userService;

        @Test
        void 로그인_필요() {

            // given
            Long id = 1L;

            given(userRepository.findById(id)).willReturn(Optional.empty());

            // when - then
            assertThrows(BusinessException.class,
                    () -> userService.getUser(id)
            );

        }

        @Test
        void 회원_아이디_불일치() {

            // given
            Long id = 1L;

            User user = User.builder()
                    .id(2L)
                    .build();

            given(userRepository.findById(id)).willReturn(Optional.of(user));

            // when - then
            assertThrows(BusinessException.class,
                    () -> userService.getUser(id)
            );

        }

    }


}
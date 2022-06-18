package spring.pracblog2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.User;
import spring.pracblog2.error.ErrorCode;
import spring.pracblog2.error.exception.BusinessException;
import spring.pracblog2.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException("Can't find " + email, ErrorCode.NO_DATA_IN_DB)
        );

        return new UserDetailsImpl(user);
    }
}
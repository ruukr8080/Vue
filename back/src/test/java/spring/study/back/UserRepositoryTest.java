package spring.study.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.study.back.entity.UserEntity;
import spring.study.back.entity.UserRepository;
import spring.study.back.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("1.유저 데이터 생성")
    void test_1() {
        String encPw = pwEncoder.encode("test_password");
        UserEntity userEntity = UserEntity.builder()
                .userId("test")
                .pw(encPw)
                .name("test")
                .build();
        UserEntity savedUserEntity = userRepository.save(userEntity);
        assertThat(userEntity.getUserId()).isEqualTo(savedUserEntity.getUserId());
    }

    @Test
    @DisplayName("2.유저정보 검색해서 비번 비교. 필터가 일 잘하는지 보는거.")
    void test_2() {

        String userId = "test";
        String pw = "test_password";
        UserDetails user = userService.loadUserByUsername(userId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, pw);
        authenticationManager.authenticate(authenticationToken);

        assertThat(authenticationToken.getCredentials()).isEqualTo(pw);

        System.out.println("getCredentials: " + authenticationToken.getCredentials());
        System.out.println("userPw: " + pw);
    }
}
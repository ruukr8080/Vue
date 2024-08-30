package spring.study.back.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("1.유저 데이터 생성")
    void test_1() {
        String encPw = pwEncoder.encode("123456");
        UserEntity userEntity = UserEntity.builder()
                .id("d")
                .pw(encPw)
                .name("sa")
                .build();
        UserEntity savedUserEntity = userRepository.save(userEntity);
        assertThat(userEntity.getId()).isEqualTo(savedUserEntity.getId());
    }
}
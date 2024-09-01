package spring.study.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.study.back.config.util.JwtUtil;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("1. 토큰 생성 후 검증")
    void test_1() {
        String userId = "user1";
        String userName = "userKim";

        String token = jwtUtil.createToken(userId, userName);

        System.out.println("토큰토큰토큰토큰토큰토큰토큰토큰"+token);
        assertThat(jwtUtil.decodeToken(token).getClaim("userName").asString()).isEqualTo(userName);
    }
}
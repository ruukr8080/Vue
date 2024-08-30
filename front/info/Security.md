# Spring-Security

## 알아둬야할 용어
- CORS
- CSRF
- SOP
## 메서드

## 구조

# front-security


# back-security
스프링 시큐리티로 강력한 보안기능을 적용해보자!

## 1.의존성 주입

```shell
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'jakarta.validation:jakarta.validation-api:3.1.0'
	implementation 'com.auth0:java-jwt:4.4.0'
```
## 2.이전에 썻던 board Table을 또 추가하고 기본 데이터 입력해주자.
```sql
CREATE TABLE IF NOT EXISTS tb_board (
idx SERIAL PRIMARY KEY,
title VARCHAR(255),
contents VARCHAR(255),
author VARCHAR(255),
created_at TIMESTAMP
);

CREATE TABLE tb_USER (
IDX BIGSERIAL PRIMARY KEY,
USER_ID VARCHAR(50) UNIQUE,
USER_PW VARCHAR(100),
USER_NAME VARCHAR(50)
);

COMMENT ON table tb_board is '게시판 테이블';
COMMENT ON TABLE tb_USER IS '유저 테이블';
```

## 3. Config
config 패키지 하위에 WebSecurityConfig.java 생성.

@EnableWebMvcSecurity
@Configuration

이 두 어노테이션을 박아줘야하는데
@EnableWebMvcSecurity
얘는 
HTTP security 설정을 할 때,
WebSecurityConfigurerAdapter를 상속 받아
configure 메서드를 오버라이딩해서
http 인증, 인가 설정해서 쓰게 해주는 앤데,
WebSecurityConfigurerAdapter 파일이 더이상 지원이 안돼서 
상속을 못한다.

이 문제는 [Spring blog](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
에서 해답을 찾을 수 있었다.
Spring Security 5.7.0-M2 부터 
사용자가 컴포넌트 기반 보안 구성으로 전환 하도록 권장하기 때문에 
더이상 WebSecurityConfigureAdapter를 사용하지 않는다고 한다.

그러니 http security 설정을 하고싶으면 
이제부터 Spring Security 5.4에 도입된 
SecurityFilterChain Bean을 생성하여 설정하라고 한다.

### 이전 Http Security 설정 방식
```javascript
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
    }
}

```
### 새로운 http Security 설정 방식
```javascript
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
.authorizeHttpRequests((authz) -> authz
.anyRequest().authenticated()
)
.httpBasic(withDefaults());
    return http.build();
}
}
```

passwordEncorder는 되는듯 
```javascript
  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```


- 비밀번호 암호화
Bean으로 등록하고 기본 http 설정해주자.



* 2. 근데 왜 또 만듬?
3. 
# front-security



# back-security

스프링 시큐리티로 강력한 보안기능을 적용해보자!

## 의존성 주입

```shell
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'jakarta.validation:jakarta.validation-api:3.1.0'
	implementation 'com.auth0:java-jwt:4.4.0'
```
## 이전에 썻던 boardTable을 또 추가하고 기본 데이터 입력해주자.
근데 왜 또 만듬?

## config 
config 패키지 하위에 WebSecurityConfig.java 생성.

## BCryptPasswordEncoder
- 비밀번호 암호화
  Bean으로 등록하고 기본 http 설정해주자.


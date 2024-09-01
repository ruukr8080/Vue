package spring.study.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spring.study.back.config.util.JwtUtil;
import spring.study.back.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> paramMap) {
        String userId = paramMap.get("userId");
        String pw = paramMap.get("pw");

        UserDetails loginUser = userService.loadUserByUsername(userId); //userId로 데이터 가져오기
        Authentication authentication = authenticationManager.authenticate( // 가져온 정보와 입력된 비번 검증
                new UsernamePasswordAuthenticationToken(loginUser, pw)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication); // 검증 통과 후
        String accessToken = jwtUtil.createToken(loginUser.getUsername(), loginUser.getUsername()); //accessToken 생성

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", loginUser.getUsername());
        result.put("user_token", accessToken);
        result.put("user_role", loginUser.getAuthorities());

        return ResponseEntity.ok(result);
    }
}

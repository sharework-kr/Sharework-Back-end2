package com.sharework.domain;

import com.sharework.domain.user.dao.UserDao;
import com.sharework.domain.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class testController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/api/hello1")
    public ResponseEntity hello3(@RequestParam String id, @RequestParam String password) {

        List<String> aa = new ArrayList<>();
        aa.add("USER");
        User user = userDao.save(User.builder().password(passwordEncoder.encode(password)).id(id).roles(aa).build());
        System.out.println(user.toString());
        return ResponseEntity.ok().body(HttpStatus.OK.name());
    }

    @GetMapping("/api/hello2")
    public String hello1(@RequestParam String id, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        System.out.println(authenticationToken.toString());
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        tokenProvider.createToken(authentication);
//        return tokenProvider.createToken(authentication).toString();
        return "123";
    }
}
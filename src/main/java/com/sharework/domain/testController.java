package com.sharework.domain;

import com.sharework.domain.user.dao.UserDao;
import com.sharework.domain.user.dto.User;
import com.sharework.global.security.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/hello1")
    public ResponseEntity hello3(@RequestParam String id, @RequestParam String password) {

        List<String> aa = new ArrayList<>();
        aa.add("USER");
        User user = userDao.save(User.builder().password(bCryptPasswordEncoder.encode(password)).id(id).roles(aa).build());
        System.out.println(user.toString());
        return ResponseEntity.ok().body(HttpStatus.OK.name());
    }

    @GetMapping("/api/hello2")
    public String hello1(@RequestParam String id, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        System.out.println(authenticationToken.toString());
        // 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println(authentication.toString());
        // 검증된 인증 정보로 JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
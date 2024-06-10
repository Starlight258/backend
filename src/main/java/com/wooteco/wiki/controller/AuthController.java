package com.wooteco.wiki.controller;

import com.wooteco.wiki.dto.AuthTokens;
import com.wooteco.wiki.dto.JoinRequest;
import com.wooteco.wiki.dto.LoginRequest;
import com.wooteco.wiki.service.AuthService;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@RequestBody LoginRequest loginRequest) {
        AuthTokens authTokens = authService.login(loginRequest);
        return makeResponseEntityWithTokenCookie(authTokens);
    }

    private ResponseEntity<AuthTokens> makeResponseEntityWithTokenCookie(AuthTokens authTokens) {
        LocalDateTime now = LocalDateTime.now();
        Duration tokenLifeTime = Duration.between(now, now.plusHours(1));
        String accessToken = authTokens.accessToken();
        ResponseCookie cookie = ResponseCookie.from("token", accessToken)
                .httpOnly(true)
                .maxAge(tokenLifeTime)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authTokens);
    }

    @PostMapping("/join")
    public ResponseEntity<AuthTokens> join(@RequestBody JoinRequest joinRequest) {
        AuthTokens authTokens = authService.join(joinRequest);
        return makeResponseEntityWithTokenCookie(authTokens);
    }
}

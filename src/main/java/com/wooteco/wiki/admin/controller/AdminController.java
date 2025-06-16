package com.wooteco.wiki.admin.controller;

import com.wooteco.wiki.admin.domain.dto.AdminResponse;
import com.wooteco.wiki.admin.domain.dto.LoginRequest;
import com.wooteco.wiki.admin.service.AdminService;
import com.wooteco.wiki.global.auth.domain.dto.TokenResponse;
import com.wooteco.wiki.global.auth.service.AuthService;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final String TOKEN_NAME_FIELD = "token";
    private final AuthService authService;
    private final AdminService adminService;

    public AdminController(AuthService authService, AdminService adminService) {
        this.authService = authService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie
                .from(TOKEN_NAME_FIELD, tokenResponse.accessToken())
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<AdminResponse> checkAuth(@CookieValue(name = TOKEN_NAME_FIELD) String token) {
        AdminResponse adminResponse = authService.findMemberByToken(token);
        return ResponseEntity.ok().body(adminResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = TOKEN_NAME_FIELD) String token) {
        authService.findMemberByToken(token);

        ResponseCookie cookie = ResponseCookie
                .from(TOKEN_NAME_FIELD, "")
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofDays(0))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocumentByDocumentId(@PathVariable Long documentId) {
        adminService.deleteDocumentByDocumentId(documentId);
        return ResponseEntity.noContent().build();
    }
}

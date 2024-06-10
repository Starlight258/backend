package com.wooteco.wiki.controller;

import static com.wooteco.wiki.exception.ExceptionType.EMAIL_DUPLICATE;
import static com.wooteco.wiki.exception.ExceptionType.EMAIL_INVALID;
import static com.wooteco.wiki.exception.ExceptionType.LOGIN_FAIL;
import static com.wooteco.wiki.exception.ExceptionType.PASSWORD_INVALID;

import com.wooteco.wiki.annotation.ApiSuccessResponse;
import com.wooteco.wiki.annotation.ErrorApiResponse;
import com.wooteco.wiki.dto.AuthTokens;
import com.wooteco.wiki.dto.JoinRequest;
import com.wooteco.wiki.dto.LoginRequest;
import com.wooteco.wiki.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "회원 가입 API. 응답으로 엑세스 토큰과 리프레쉬 토큰을 반환한다. 쿠키에 httpOnly 엑세스 토큰이 저장되어있다.")
    @ErrorApiResponse({LOGIN_FAIL, EMAIL_INVALID, PASSWORD_INVALID})
    @ApiSuccessResponse(bodyType = AuthTokens.class, body = """
            {
                "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTgwMDIzODAsInRva2VuX3R5cGUiOiJhY2Nlc3MiLCJtZW1iZXJfaWQiOjJ9.9cxQ2XFgdXApD8aqE4a74kJsJ37luCW34ptXl2VGaQ6t-vEhvgLyd8gnTB162iJPZhhbFb9OP9J_Bagjmo7Qmw",
                "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTgwODUxODAsInRva2VuX3R5cGUiOiJyZWZyZXNoIiwibWVtYmVyX2lkIjoyfQ.MozmvIX38mBfjzVwA2ehBat9hrizUIKzNbh2hDozQeDwLVxgaH0KcHzBic3pvLHINaYOvf2TpH9q4vUKa9QR4w"
            }
            """)
    public ResponseEntity<AuthTokens> login(@RequestBody LoginRequest loginRequest) {
        AuthTokens authTokens = authService.login(loginRequest);
        return makeResponseEntityWithTokenCookie(authTokens);
    }

    private ResponseEntity<AuthTokens> makeResponseEntityWithTokenCookie(AuthTokens authTokens) {
        LocalDateTime now = LocalDateTime.now();
        Duration tokenLifeTime = Duration.between(now, now.plusMinutes(30));
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
    @Operation(description = "회원 가입 API. 응답으로 엑세스 토큰과 리프레쉬 토큰을 반환한다. 쿠키에 httpOnly 엑세스 토큰이 저장되어있다.")
    @ErrorApiResponse({EMAIL_DUPLICATE, EMAIL_INVALID, PASSWORD_INVALID})
    @ApiSuccessResponse(bodyType = AuthTokens.class, body = """
            {
                "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTgwMDIzODAsInRva2VuX3R5cGUiOiJhY2Nlc3MiLCJtZW1iZXJfaWQiOjJ9.9cxQ2XFgdXApD8aqE4a74kJsJ37luCW34ptXl2VGaQ6t-vEhvgLyd8gnTB162iJPZhhbFb9OP9J_Bagjmo7Qmw",
                "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTgwODUxODAsInRva2VuX3R5cGUiOiJyZWZyZXNoIiwibWVtYmVyX2lkIjoyfQ.MozmvIX38mBfjzVwA2ehBat9hrizUIKzNbh2hDozQeDwLVxgaH0KcHzBic3pvLHINaYOvf2TpH9q4vUKa9QR4w"
            }
            """)
    public ResponseEntity<AuthTokens> join(@RequestBody JoinRequest joinRequest) {
        AuthTokens authTokens = authService.join(joinRequest);
        return makeResponseEntityWithTokenCookie(authTokens);
    }
}

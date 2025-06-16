package com.wooteco.wiki.global.config.interceptor;

import com.wooteco.wiki.global.auth.JwtTokenProvider;
import com.wooteco.wiki.global.auth.Role;
import com.wooteco.wiki.global.auth.exception.CookieNotFoundException;
import com.wooteco.wiki.global.exception.ForbiddenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private static final String TOKEN_NAME_FILED = "token";

    private final JwtTokenProvider jwtTokenProvider;

    public LoginCheckInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractTokenFromCookie(request);

        if (token == null) {
            throw new CookieNotFoundException();
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new ForbiddenException("유효하지 않는 토큰입니다.");
        }

        Claims claims = jwtTokenProvider.getClaims(token);
        String roleStr = (String) claims.get("role");

        if (!roleStr.equals(Role.ROLE_ADMIN.name())) {
            throw new ForbiddenException();
        }
        return true;
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (TOKEN_NAME_FILED.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

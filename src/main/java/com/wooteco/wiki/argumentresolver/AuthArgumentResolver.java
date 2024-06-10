package com.wooteco.wiki.argumentresolver;

import com.wooteco.wiki.annotation.Auth;
import com.wooteco.wiki.domain.TokenManager;
import com.wooteco.wiki.exception.WrongTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final int VALID_TOKEN_COOKIE_COUNT = 1;
    private final TokenManager tokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie[] cookies = nativeRequest.getCookies();
        validateTokenCookieCount(cookies);
        String accessToken = Arrays.stream(cookies)
                .filter(this::isTokenCookie)
                .limit(VALID_TOKEN_COOKIE_COUNT)
                .findAny()
                .map(Cookie::getValue)
                .orElseThrow(() -> new WrongTokenException("토큰이 잘못되었습니다."));
        return tokenManager.extractMemberId(accessToken);
    }

    private void validateTokenCookieCount(Cookie[] cookies) {
        long tokenCookieCount = Arrays.stream(cookies)
                .filter(this::isTokenCookie)
                .count();
        if (tokenCookieCount != VALID_TOKEN_COOKIE_COUNT) {
            throw new WrongTokenException("토큰이 잘못되었습니다.");
        }
    }

    private boolean isTokenCookie(Cookie cookie) {
        return cookie.getName().equals("token");
    }
}

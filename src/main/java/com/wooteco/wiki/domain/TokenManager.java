package com.wooteco.wiki.domain;

import com.wooteco.wiki.exception.WrongTokenException;

public class TokenManager {

    /**
     * 생성된 엑세스 토큰은 리프레쉬 토큰으로 사용할 수 없어야 함. 엑세스 토큰에는 회원의 식별자를 제외한 다른 개인 정보가 포함되면 안됨.
     *
     * @param member 회원 도메인
     * @return 엑세스 토큰
     */
    public static String generateAccessToken(Member member) {
        return null;
    }

    /**
     * 생성된 리프레쉬 토큰은 엑세스 토큰으로 사용할 수 없어야 함.
     *
     * @param member 회원 도메인
     * @return 리프레쉬 토큰
     */
    public static String generateRefreshToken(Member member) {
        return null;
    }

    /**
     * 회원 정보로부터 회원의 식별자를 알아냄.
     *
     * @param accessToken 엑세스 토큰
     * @return 회원의 식별자.
     * @throws WrongTokenException 엑세스 토큰에 문제(잘못된 토큰, 기간 만료 등)가 있어 회원 식별자를 추출할 수 없는 경우
     */
    public static long extractMemberId(String accessToken) throws WrongTokenException {
        return -1;
    }
}

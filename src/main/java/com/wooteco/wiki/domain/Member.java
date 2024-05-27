package com.wooteco.wiki.domain;

import static com.wooteco.wiki.domain.MemberState.INITIAL;
import static com.wooteco.wiki.domain.MemberState.WAITING;

import com.wooteco.wiki.util.Sha256Encryptor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberState state;

    @Builder
    public Member(Long memberId, String nickname, String email, String password, MemberState state) {
        password = Sha256Encryptor.encrypt(password);
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.state = state;
    }

    public void updateEmailAndPasswordWhenINITIAL(String email, String password) {
        if (INITIAL.equals(state)) {
            this.email = email;
            this.password = Sha256Encryptor.encrypt(password);
            state = WAITING;
        }
    }
}

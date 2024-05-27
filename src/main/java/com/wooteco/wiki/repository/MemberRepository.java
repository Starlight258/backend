package com.wooteco.wiki.repository;

import com.wooteco.wiki.domain.Member;
import com.wooteco.wiki.domain.MemberState;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

    Optional<Member> findByNicknameAndState(String nickName, MemberState state);
}

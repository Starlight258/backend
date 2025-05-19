package com.wooteco.wiki.repository;

import com.wooteco.wiki.domain.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findOneByLoginIdAndPassword(String loginId, String password);
}

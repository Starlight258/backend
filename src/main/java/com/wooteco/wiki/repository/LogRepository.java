package com.wooteco.wiki.repository;

import com.wooteco.wiki.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}

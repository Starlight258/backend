package com.wooteco.wiki.log.repository;

import com.wooteco.wiki.log.domain.Log;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findAllByUuidOrderByIdAsc(UUID uuid);
}

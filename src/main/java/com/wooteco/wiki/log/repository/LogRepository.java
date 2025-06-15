package com.wooteco.wiki.log.repository;

import com.wooteco.wiki.log.domain.Log1;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log1, Long> {

    List<Log1> findAllByUuidOrderByIdAsc(@NotNull UUID uuid);
}

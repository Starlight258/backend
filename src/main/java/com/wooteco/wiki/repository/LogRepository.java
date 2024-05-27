package com.wooteco.wiki.repository;

import com.wooteco.wiki.domain.Log;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findAllByTitleOrderByLogIdAsc(String title);
}

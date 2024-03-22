package com.wooteco.wiki.repository;

import com.wooteco.wiki.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

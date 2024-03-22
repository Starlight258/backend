package com.wooteco.wiki.service;

import com.wooteco.wiki.dto.PostCreateRequest;
import com.wooteco.wiki.dto.PostResponse;
import java.util.Optional;

public interface PostService {
    PostResponse post(String title, PostCreateRequest postCreateRequest);

    Optional<PostResponse> get(String title);
}

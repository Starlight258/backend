package com.wooteco.wiki.service;

import com.wooteco.wiki.dto.PostCreateRequest;
import com.wooteco.wiki.dto.PostResponse;

public interface PostService {
    PostResponse post(String title, PostCreateRequest postCreateRequest);
}

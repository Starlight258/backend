package com.wooteco.wiki.controller;

import com.wooteco.wiki.dto.PostCreateRequest;
import com.wooteco.wiki.dto.PostResponse;
import com.wooteco.wiki.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{title}")
    public ResponseEntity<PostResponse> post(@PathVariable String title,
                                             @RequestBody PostCreateRequest postCreateRequest) {
        PostResponse post = postService.post(title, postCreateRequest);
        return ResponseEntity.ok(post);
    }
}

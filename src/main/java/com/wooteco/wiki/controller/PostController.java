package com.wooteco.wiki.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.wooteco.wiki.dto.ErrorResponse;
import com.wooteco.wiki.dto.PostCreateRequest;
import com.wooteco.wiki.dto.PostResponse;
import com.wooteco.wiki.service.PostService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{title}")
    public ResponseEntity<?> get(@PathVariable String title) {
        Optional<PostResponse> postResponse = postService.get(title);
        if (postResponse.isEmpty()) {
            return notFound();
        }
        return ResponseEntity.ok(postResponse.get());
    }

    private ResponseEntity<ErrorResponse> notFound() {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ErrorResponse("없는 문서입니다."));
    }
}

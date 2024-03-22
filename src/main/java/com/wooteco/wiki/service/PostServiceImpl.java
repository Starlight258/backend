package com.wooteco.wiki.service;

import com.wooteco.wiki.dto.PostCreateRequest;
import com.wooteco.wiki.dto.PostResponse;
import com.wooteco.wiki.entity.Post;
import com.wooteco.wiki.repository.PostRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponse post(String title, PostCreateRequest postCreateRequest) {
        String contents = postCreateRequest.contents();
        String writer = postCreateRequest.writer();
        Post post = Post.builder()
                .title(title)
                .contents(contents)
                .writer(writer)
                .generateTime(LocalDateTime.now())
                .build();
        Post save = postRepository.save(post);
        return mapToResponse(save);
    }

    private PostResponse mapToResponse(Post post) {
        long postId = post.getPostId();
        String title = post.getTitle();
        String contents = post.getContents();
        String writer = post.getWriter();
        LocalDateTime generateTime = post.getGenerateTime();
        return new PostResponse(postId, title, contents, writer, generateTime);
    }
}

package com.wooteco.wiki.global.config.aws;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final AwsS3Uploader awsS3Uploader;

    @GetMapping("/upload/**")
    public String upload(HttpServletRequest request) {
        String fileKey = request.getRequestURI().substring("/upload/".length());
        return awsS3Uploader.createPresignedUrl(fileKey).toString();
    }
}


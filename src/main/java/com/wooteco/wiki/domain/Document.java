package com.wooteco.wiki.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    @Column(unique = true)
    private String title;
    private String contents;
    private Long documentBytes;
    private LocalDateTime generateTime;
    @ManyToOne
    private Member writer;

    public void update(String contents, Member writer, Long documentBytes, LocalDateTime generateTime) {
        this.contents = contents;
        this.writer = writer;
        this.documentBytes = documentBytes;
        this.generateTime = generateTime;
    }
}

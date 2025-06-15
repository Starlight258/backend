package com.wooteco.wiki.log.domain;

import com.wooteco.wiki.document.domain.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
@Entity
public class Log1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private UUID uuid;
    private String contents;
    private String writer;
    private long documentBytes;
    private LocalDateTime generateTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    protected Log1() {
    }

    public Log1(String title, UUID uuid, String contents, String writer, long documentBytes, LocalDateTime generateTime, Document document) {
        this.title = title;
        this.uuid = uuid;
        this.contents = contents;
        this.writer = writer;
        this.documentBytes = documentBytes;
        this.generateTime = generateTime;
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Log1 that)) {
            return false;
        }
        if (this.id == null || that.id == null) {
            return false;
        }
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

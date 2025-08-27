package com.wooteco.wiki.organizationdocument.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrganizationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: 사건, 로그 -> (인터페이스화 할 것인지 기본 문서의 로그와 조직 문서의 로그는 다른가? 고민 후 칼럼 생성), 검색(위키 검색 시 문서, 조직문서 상관 없이 다 떠야하는데, 현재는 Document만 뜨게 되어있음.)
    // 도 생각해야 함.

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contents;

    private String writer;

    @Column(name = "document_bytes")
    private Long documentBytes;

    @JdbcTypeCode(Types.CHAR)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "generate_time")
    LocalDateTime generateTime;

    public OrganizationDocument(String title, String contents, String writer, Long documentBytes, UUID uuid,
                                LocalDateTime generateTime) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.documentBytes = documentBytes;
        this.uuid = uuid;
        this.generateTime = generateTime;
    }

    public OrganizationDocument(String title, String contents, String writer, Long documentBytes, UUID uuid) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.documentBytes = documentBytes;
        this.uuid = uuid;
        this.generateTime = LocalDateTime.now();
    }

    public void update(String title, String contents, String writer, Long documentBytes, LocalDateTime generateTime) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.documentBytes = documentBytes;
        this.generateTime = generateTime;
    }
}

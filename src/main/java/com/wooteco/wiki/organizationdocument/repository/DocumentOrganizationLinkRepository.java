package com.wooteco.wiki.organizationdocument.repository;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentOrganizationLinkRepository extends
        JpaRepository<DocumentOrganizationLink, Long> {

    Optional<DocumentOrganizationLink> findByDocumentAndOrganizationDocument(
            Document document,
            OrganizationDocument organizationDocument
    );

    void deleteByDocumentAndOrganizationDocument(Document document, OrganizationDocument organizationDocument);

    List<DocumentOrganizationLink> findAllByDocument(Document document);
}

package com.wooteco.wiki.organizationdocument.repository;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationDocumentLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentOrganizationDocumentLinkRepository extends
        JpaRepository<DocumentOrganizationDocumentLink, Long> {

    DocumentOrganizationDocumentLink findByDocumentAndOrganizationDocument(
            Document document,
            OrganizationDocument organizationDocument
    );

    void deleteByDocumentAndOrganizationDocument(Document document, OrganizationDocument organizationDocument);

    List<DocumentOrganizationDocumentLink> findAllByDocument(Document document);
}

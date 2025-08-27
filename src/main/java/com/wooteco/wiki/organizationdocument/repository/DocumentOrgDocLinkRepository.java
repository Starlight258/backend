package com.wooteco.wiki.organizationdocument.repository;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrgDocLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentOrgDocLinkRepository extends JpaRepository<DocumentOrgDocLink, Long> {
    DocumentOrgDocLink findByDocumentAndOrganizationDocument(Document document,
                                                             OrganizationDocument organizationDocument);
}

package com.wooteco.wiki.document.repository

import com.wooteco.wiki.document.domain.Document
import com.wooteco.wiki.document.domain.Title
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DocumentRepository : JpaRepository<Document, Long> {

    fun findByTitle(title: String): Optional<Document>

    fun existsByTitle(title: String): Boolean

    fun findAllByOrderByGenerateTimeDesc(): List<Document>

    fun findAllByTitleStartingWith(keyWord: String): List<Title>

    fun findByUuid(uuid: UUID): Optional<Document>

    @Query("SELECT d.uuid FROM Document d WHERE d.title = :title")
    fun findUUidByTitle(@Param("title") title: String): Optional<UUID>
}

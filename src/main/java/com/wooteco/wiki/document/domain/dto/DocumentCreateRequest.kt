package com.wooteco.wiki.document.domain.dto

data class DocumentCreateRequest(
    val title: String,
    val contents: String,
    val writer: String,
    val documentBytes: Long,
)

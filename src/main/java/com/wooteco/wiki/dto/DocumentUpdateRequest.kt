package com.wooteco.wiki.dto

data class DocumentUpdateRequest(
    val contents: String,
    val writer: String,
    val documentBytes: Long,
)

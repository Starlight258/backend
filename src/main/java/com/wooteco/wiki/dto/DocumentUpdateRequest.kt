package com.wooteco.wiki.dto

data class DocumentUpdateRequest(
    val title: String,
    val contents: String,
    val writer: String,
    val documentBytes: Long,
)

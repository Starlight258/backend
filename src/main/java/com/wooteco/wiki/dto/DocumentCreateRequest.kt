package com.wooteco.wiki.dto

data class DocumentCreateRequest(
    val title: String,
    val contents: String,
    val writer: String,
    val documentBytes: Long,
)

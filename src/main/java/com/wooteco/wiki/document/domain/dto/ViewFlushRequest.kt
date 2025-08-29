package com.wooteco.wiki.document.domain.dto

import java.util.UUID

data class ViewFlushRequest(
    val views: Map<UUID, Int>
)

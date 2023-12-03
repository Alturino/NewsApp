package com.onirutla.newsapp.domain.models

import com.onirutla.newsapp.core.source.remote.models.SourceResponse

data class Source(
    val id: String = "",
    val name: String = "",
)

fun Source.toSourceResponse() = SourceResponse(
    id = id,
    name = name
)
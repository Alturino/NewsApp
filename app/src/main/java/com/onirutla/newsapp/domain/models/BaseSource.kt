package com.onirutla.newsapp.domain.models

data class BaseSource(
    val status: String = "",
    val sources: List<SourceDetail> = listOf(),
)

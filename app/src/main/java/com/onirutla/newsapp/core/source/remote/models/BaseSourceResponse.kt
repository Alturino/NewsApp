package com.onirutla.newsapp.core.source.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseSourceResponse(
    @SerialName("status")
    val status: String? = "",
    @SerialName("sources")
    val sources: List<SourceResponseDetail?>? = listOf(),
)

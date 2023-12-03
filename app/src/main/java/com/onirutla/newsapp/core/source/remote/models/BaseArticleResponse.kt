package com.onirutla.newsapp.core.source.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseArticleResponse(
    @SerialName("articles")
    val articleResponses: List<ArticleResponse?>? = listOf(),
    @SerialName("code")
    val code: String? = "",
    @SerialName("message")
    val message: String? = "",
    @SerialName("status")
    val status: String? = "",
    @SerialName("totalResults")
    val totalResults: Int? = 0,
)
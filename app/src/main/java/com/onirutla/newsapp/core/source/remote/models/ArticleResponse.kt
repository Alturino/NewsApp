package com.onirutla.newsapp.core.source.remote.models

import com.onirutla.newsapp.domain.models.Article
import com.onirutla.newsapp.domain.models.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("author")
    val author: String? = "",
    @SerialName("content")
    val content: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("publishedAt")
    val publishedAt: String? = "",
    @SerialName("source")
    val sourceResponse: SourceResponse? = SourceResponse(),
    @SerialName("title")
    val title: String? = "",
    @SerialName("url")
    val url: String? = "",
    @SerialName("urlToImage")
    val urlToImage: String? = "",
)

fun ArticleResponse.toArticle() = Article(
    author = author.orEmpty(),
    content = content.orEmpty(),
    description = description.orEmpty(),
    publishedAt = publishedAt.orEmpty(),
    source = sourceResponse?.toSource() ?: Source(),
    title = title.orEmpty(),
    url = url.orEmpty(),
    urlToImage = urlToImage.orEmpty()
)
fun List<ArticleResponse?>?.toArticle(): List<Article> = if(this.isNullOrEmpty()) listOf() else mapNotNull { it?.toArticle() }

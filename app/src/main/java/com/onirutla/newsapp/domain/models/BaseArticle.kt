package com.onirutla.newsapp.domain.models

data class BaseArticle(
    val articles: List<Article> = listOf(),
    val code: String = "",
    val message: String = "",
    val status: String = "",
    val totalResults: Int = 0,
)
package com.onirutla.newsapp.domain.models

data class Article(
    val id: Int = 0,
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val category: String = "",
    val countryCode: String = "",
)

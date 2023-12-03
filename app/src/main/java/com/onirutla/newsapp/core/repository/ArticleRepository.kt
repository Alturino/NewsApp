package com.onirutla.newsapp.core.repository

import com.onirutla.newsapp.core.source.remote.ArticleRemoteDataSource
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.domain.models.Article
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val remote: ArticleRemoteDataSource,
) {
    suspend fun getTopHeadlines(
        category: NewsSourceCategory? = null,
        countryCode: String? = null,
        pageNumber: Int? = 1,
        pageSize: Int? = 50,
        query: String? = null,
        sources: String? = null,
    ): List<Article> {
        return remote.getTopHeadlines(
            category = category,
            countryCode = countryCode,
            pageNumber = pageNumber,
            pageSize = pageSize,
            query = query,
            sources = sources
        )
    }
}
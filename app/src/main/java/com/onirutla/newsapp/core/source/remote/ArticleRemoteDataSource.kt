package com.onirutla.newsapp.core.source.remote

import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.onirutla.newsapp.core.source.remote.api_services.NewsApiService
import com.onirutla.newsapp.core.source.remote.api_services.NewsSearchIn
import com.onirutla.newsapp.core.source.remote.api_services.NewsSortBy
import com.onirutla.newsapp.core.source.remote.models.toArticle
import com.onirutla.newsapp.domain.models.Article
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import timber.log.Timber
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(private val apiService: NewsApiService) {
    suspend fun getEverything(
        fromDateTime: LocalDateTime? = null,
        pageNumber: Int? = 1,
        pageSize: Int? = 50,
        query: String,
        searchIn: List<NewsSearchIn>? = null,
        sortBy: NewsSortBy? = null,
        sources: String? = null,
        toDateTime: LocalDateTime? = null,
    ): List<Article> {
        val response = apiService.getEverything(
            fromDateTime = fromDateTime?.toJavaLocalDateTime()
                ?.format(DateTimeFormatter.ISO_DATE),
            pageNumber = pageNumber,
            pageSize = pageSize,
            query = query,
            searchIn = searchIn?.joinToString(separator = ",", transform = { it.value }),
            sortBy = sortBy?.value,
            sources = sources,
            toDateTime = toDateTime?.toJavaLocalDateTime()
                ?.format(DateTimeFormatter.ISO_DATE),
        ).onLeft {
            when (it) {
                is HttpError -> {
                    Timber.e("message: ${it.message}, code: ${it.code}, body: ${it.body}")
                }

                is IOError -> Timber.e(it.cause)
                is UnexpectedCallError -> Timber.e(it.cause)
            }
        }.onRight { Timber.d("$it") }
            .map { it.articleResponses.toArticle() }

        return response.fold(ifLeft = { listOf() }, ifRight = { it })
    }

    suspend fun getTopHeadlines(
        param: GetTopHeadlineParam,
    ): List<Article> {
        val response = apiService.getTopHeadlines(
            pageNumber = param.pageNumber,
            pageSize = param.pageSize,
            query = param.query,
            category = param.category?.value,
            countryCode = param.countryCode,
            sources = param.sources,
        ).onLeft {
            when (it) {
                is HttpError -> {
                    Timber.e("message: ${it.message}, code: ${it.code}, body: ${it.body}")
                }

                is IOError -> Timber.e(it.cause)
                is UnexpectedCallError -> Timber.e(it.cause)
            }
        }.onRight { Timber.d("$it") }
            .map { it.articleResponses.toArticle() }

        return response.fold(ifLeft = { listOf() }, ifRight = { it })
    }
}
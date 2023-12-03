package com.onirutla.newsapp.core.repository

import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.onirutla.newsapp.core.source.remote.api_services.NewsApiService
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.core.source.remote.models.toSourceDetail
import com.onirutla.newsapp.domain.models.SourceDetail
import timber.log.Timber
import javax.inject.Inject

class SourceRepository @Inject constructor(private val apiService: NewsApiService) {
    suspend fun getTopHeadlineSources(
        category: NewsSourceCategory,
        countryCode: String,
        language: String,
    ): List<SourceDetail> {
        val response = apiService.getTopHeadlineSources(
            category = category.value,
            countryCode = countryCode,
            language = language
        ).onLeft {
            when (it) {
                is HttpError -> {
                    Timber.e("message: ${it.message}, code: ${it.code}, body: ${it.body}")
                }

                is IOError -> Timber.e(it.cause)
                is UnexpectedCallError -> Timber.e(it.cause)
            }
        }.onRight { Timber.d("$it") }
            .map { it.sources.toSourceDetail() }

        return response.fold(ifLeft = { listOf() }, ifRight = { it })
    }
}
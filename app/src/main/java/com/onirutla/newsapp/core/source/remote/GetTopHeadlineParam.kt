package com.onirutla.newsapp.core.source.remote

import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory

data class GetTopHeadlineParam(
    val category: NewsSourceCategory? = null,
    val countryCode: String? = null,
    val pageNumber: Int = 1,
    val pageSize: Int = 50,
    val query: String? = null,
    val sources: String? = null,
)
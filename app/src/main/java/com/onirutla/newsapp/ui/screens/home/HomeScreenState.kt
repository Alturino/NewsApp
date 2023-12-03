package com.onirutla.newsapp.ui.screens.home

import androidx.compose.ui.text.intl.Locale
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.domain.models.Article
import com.onirutla.newsapp.ui.ResultState

data class HomeScreenState(
    val articles: ResultState<List<Article>> = ResultState.InitialState(listOf()),
    val selectedFilterIndex: Int = 0,
    val isLoading: Boolean? = null,
    val query: String = "",
    val currentLocale: Locale = Locale.current,
    val category: NewsSourceCategory = NewsSourceCategory.entries[selectedFilterIndex],
)

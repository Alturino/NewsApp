package com.onirutla.newsapp.ui.screens.home

import androidx.compose.ui.text.intl.Locale
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory

data class HomeScreenState(
    val selectedFilterIndex: Int = 0,
    val isLoading: Boolean? = null,
    val query: String = "",
    val currentLocale: Locale = Locale.current,
    val category: NewsSourceCategory = NewsSourceCategory.entries[selectedFilterIndex],
)

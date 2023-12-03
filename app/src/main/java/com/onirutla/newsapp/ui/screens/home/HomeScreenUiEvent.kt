package com.onirutla.newsapp.ui.screens.home

import com.onirutla.newsapp.domain.models.Article

sealed interface HomeScreenUiEvent {
    data class OnItemClick(val article: Article) : HomeScreenUiEvent
}
package com.onirutla.newsapp.ui.screens.home

import androidx.compose.ui.text.intl.Locale

sealed interface HomeScreenEvent {
    data class OnFilterChipClick(val index: Int) : HomeScreenEvent
    data class OnLocaleChanges(val locale: Locale) : HomeScreenEvent
    data class OnQueryChanges(val query: String) : HomeScreenEvent
}

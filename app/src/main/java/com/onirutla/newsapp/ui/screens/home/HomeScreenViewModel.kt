package com.onirutla.newsapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onirutla.newsapp.core.repository.ArticleRepository
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.ui.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    operator fun invoke() {
        viewModelScope.launch {
            _state.update { it.copy(articles = ResultState.Loading) }

            val initialArticle = articleRepository.getTopHeadlines(
                category = _state.value.category,
                countryCode = _state.value.currentLocale.region
            )

            _state.update { it.copy(articles = ResultState.Success(initialArticle)) }
        }
    }

    init {
        viewModelScope.launch {
            _state.map { it.query }
                .onStart { _state.update { it.copy(articles = ResultState.Loading) } }
                .debounce(500)
                .mapLatest {
                    articleRepository.getTopHeadlines(
                        query = it,
                        category = _state.value.category,
                        countryCode = _state.value.currentLocale.region,
                    )
                }
                .collectLatest { articles ->
                    _state.update { it.copy(articles = ResultState.Success(articles)) }
                }
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnFilterChipClick -> {
                _state.update {
                    it.copy(
                        articles = ResultState.Loading,
                        selectedFilterIndex = event.index,
                        category = NewsSourceCategory.entries[event.index]
                    )
                }
            }

            is HomeScreenEvent.OnLocaleChanges -> {
                _state.update {
                    it.copy(
                        currentLocale = event.locale,
                        articles = ResultState.Loading
                    )
                }
            }

            is HomeScreenEvent.OnQueryChanges -> {
                _state.updateAndGet { it.copy(query = event.query) }
            }
        }
    }


}

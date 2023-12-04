package com.onirutla.newsapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.onirutla.newsapp.core.repository.ArticleRepository
import com.onirutla.newsapp.core.source.remote.GetTopHeadlineParam
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.domain.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    val articles: StateFlow<PagingData<Article>> = _state.map { it.query }
        .debounce(500)
        .flatMapLatest {
            articleRepository.getTopHeadlinesPaging(
                GetTopHeadlineParam(
                    query = it,
                    category = _state.value.category,
                    countryCode = _state.value.currentLocale.region,
                )
            )
        }.cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnFilterChipClick -> {
                _state.update {
                    it.copy(
                        selectedFilterIndex = event.index,
                        category = NewsSourceCategory.entries[event.index]
                    )
                }
            }

            is HomeScreenEvent.OnLocaleChanges -> {
                _state.update { it.copy(currentLocale = event.locale) }
            }

            is HomeScreenEvent.OnQueryChanges -> {
                _state.updateAndGet { it.copy(query = event.query) }
            }
        }
    }


}

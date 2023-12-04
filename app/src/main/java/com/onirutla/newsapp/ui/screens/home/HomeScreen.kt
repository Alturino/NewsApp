package com.onirutla.newsapp.ui.screens.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.domain.models.Article
import com.onirutla.newsapp.ui.components.ArticleListItem
import com.onirutla.newsapp.ui.dummy.dummyArticles
import com.onirutla.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onEvent: (HomeScreenEvent) -> Unit,
    onUiEvent: (HomeScreenUiEvent) -> Unit,
    state: HomeScreenState,
    articles: LazyPagingItems<Article>,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val shouldShowToTopButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    LaunchedEffect(key1 = Locale.current, block = {
        onEvent(HomeScreenEvent.OnLocaleChanges(Locale.current))
    })
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .statusBarsPadding()
            .navigationBarsPadding()
            .safeContentPadding(),
        contentWindowInsets = WindowInsets(
            left = 16.dp,
            top = 16.dp,
            right = 16.dp,
            bottom = 16.dp
        ),
        floatingActionButton = {
            AnimatedVisibility(
                visible = shouldShowToTopButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = { coroutineScope.launch { listState.scrollToItem(0) } }
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    value = state.query,
                    onValueChange = { onEvent(HomeScreenEvent.OnQueryChanges(it)) }
                )
            }
            item {
                LazyRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    itemsIndexed(items = NewsSourceCategory.entries) { index, item ->
                        FilterChip(
                            selected = index == state.selectedFilterIndex,
                            onClick = { onEvent(HomeScreenEvent.OnFilterChipClick(index)) },
                            label = { Text(text = item.value.replaceFirstChar { it.uppercaseChar() }) }
                        )
                    }
                }
            }

            items(count = articles.itemCount) {
                ArticleListItem(article = articles[it] ?: Article())
            }
            when (articles.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                }

                is LoadState.NotLoading -> {

                }
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun MainScreenPreview() {
    NewsAppTheme {
        HomeScreen(
            state = HomeScreenState(),
            onEvent = {},
            onUiEvent = {},
            articles = flowOf(PagingData.from(dummyArticles)).collectAsLazyPagingItems()
        )
    }
}

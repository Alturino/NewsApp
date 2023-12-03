package com.onirutla.newsapp.ui.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onirutla.newsapp.core.source.remote.api_services.NewsSourceCategory
import com.onirutla.newsapp.ui.ResultState
import com.onirutla.newsapp.ui.components.ArticleListItem
import com.onirutla.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onEvent: (HomeScreenEvent) -> Unit,
    onUiEvent: (HomeScreenUiEvent) -> Unit,
    state: HomeScreenState,
) {
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
    ) { paddingValues ->
        when (val articleState = state.articles) {
            ResultState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResultState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                    items(items = articleState.data, key = { it.id }) {
                        ArticleListItem(
                            modifier = Modifier
                                .clickable { onUiEvent(HomeScreenUiEvent.OnItemClick(it)) }
                                .animateItemPlacement(animationSpec = tween(250)),
                            article = it
                        )
                    }
                }
            }

            else -> {}
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
        HomeScreen(state = HomeScreenState(), onEvent = {}, onUiEvent = {})
    }
}

package com.onirutla.newsapp.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.onirutla.newsapp.core.source.remote.ArticleRemoteDataSource
import com.onirutla.newsapp.core.source.remote.CustomPagingSource
import com.onirutla.newsapp.core.source.remote.GetTopHeadlineParam
import com.onirutla.newsapp.domain.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val remote: ArticleRemoteDataSource,
) {
    private suspend fun getTopHeadlines(param: GetTopHeadlineParam): List<Article> =
        remote.getTopHeadlines(param = param)

    fun getTopHeadlinesPaging(param: GetTopHeadlineParam): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 50),
        pagingSourceFactory = {
            CustomPagingSource { getTopHeadlines(param.copy(pageNumber = it, pageSize = 50)) }
        }
    ).flow
}
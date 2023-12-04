package com.onirutla.newsapp.core.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CustomPagingSource<T : Any>(
    private inline val request: suspend (pageNumber: Int) -> List<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageNumber = params.key ?: 1
            val response = request(pageNumber)

            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber == 1) null else pageNumber.minus(1),
                nextKey = if (response.isEmpty()) null else pageNumber.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
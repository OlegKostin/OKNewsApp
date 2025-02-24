package com.olegkos.newsdata.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsapi.utils.ApiResult
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.utils.toTotalResultArticles
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Article>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    val page = params.key ?: 1
    val pageSize = params.loadSize
    return try {
      val response = remoteDataSource.getNews(page, pageSize)
      if (response is ApiResult.Success) {
        val articles = response.data.toTotalResultArticles().articles
        LoadResult.Page(
          data = articles,
          prevKey = if (page == 1) null else page.minus(1),
          nextKey = if (articles.size < pageSize) null else page.plus(1)
        )
      } else {
        LoadResult.Error((response as ApiResult.Error).exception)
      }
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}
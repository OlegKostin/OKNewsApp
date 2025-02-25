package com.olegkos.newsdata.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.olegkos.news_database.NewsDataBase
import com.olegkos.news_database.models.ArticleEntity
import com.olegkos.news_database.models.RemoteKeys
import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsapi.utils.ApiResult
import com.olegkos.newsdata.utils.toArticleEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
  private val remoteDataSource: RemoteDataSource,
  private val newsDataBase: NewsDataBase
) : RemoteMediator<Int, ArticleEntity>() {

  private val articlesDao = newsDataBase.articleDao()
  private val remoteKeysDao = newsDataBase.remoteKeysDao()

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, ArticleEntity>
  ): MediatorResult {
    return try {
      val currentPage = when (loadType) {
        LoadType.REFRESH -> {
          val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
          remoteKeys?.nextPage?.minus(1) ?: 1
        }

        LoadType.PREPEND -> {
          val remoteKeys = getRemoteKeyForFirstItem(state)
          remoteKeys?.prevPage ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        LoadType.APPEND -> {
          val remoteKeys = getRemoteKeyForLastItem(state)
          remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
        }
      }
      val response = remoteDataSource.getNews(
        page = currentPage,
        loadSize = state.config.pageSize
      )

      if (response !is ApiResult.Success) {
        return MediatorResult.Error((response as ApiResult.Error).exception)
      }

      val articles = response.data.articles.map {
        it.toArticleEntity()
      }
      val endOfPaginationReached = articles.isEmpty()

      newsDataBase.withTransaction {
        if (loadType == LoadType.REFRESH) {
          articlesDao.deleteAllArticles()
          remoteKeysDao.deleteAllRemoteKeys()
        }

        val keys = articles.distinctBy { it.url }.map { article ->
          RemoteKeys(
            id = article.url ?: article.title.hashCode().toString(),
            prevPage = if (currentPage == 1) null else currentPage - 1,
            nextPage = if (endOfPaginationReached) null else currentPage + 1
          )
        }
        remoteKeysDao.addAllRemoteKeys(keys)
        articlesDao.addArticles(articles)
      }

      MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    } catch (e: Exception) {
      MediatorResult.Error(e)
    }
  }

  private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)?.url?.let { id ->
        remoteKeysDao.getRemoteKeys(id)
      }
    }
  }

  private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
    return state.pages.firstOrNull { it.data.isNotEmpty() }
      ?.data?.firstOrNull()
      ?.let { article ->
        remoteKeysDao.getRemoteKeys(
          article.url ?: article.title.hashCode().toString()
        )
      }
  }

  private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
    return state.pages.lastOrNull { it.data.isNotEmpty() }
      ?.data?.lastOrNull()
      ?.let { article ->
        remoteKeysDao.getRemoteKeys(
          article.url ?: article.title.hashCode().toString()
        )
      }
  }
}

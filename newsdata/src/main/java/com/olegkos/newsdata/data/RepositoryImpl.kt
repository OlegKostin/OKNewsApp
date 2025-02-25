package com.olegkos.newsdata.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.olegkos.news_database.NewsDataBase
import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.utils.toArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
  private val newsDatabase: NewsDataBase,
  private val remoteDataSource: RemoteDataSource
) : Repository {

  @OptIn(ExperimentalPagingApi::class)
  override fun getNews(pageSize: Int): Flow<PagingData<Article>> {
    return Pager(
      config = PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = false,
        prefetchDistance = pageSize / 2
      ),
      remoteMediator = NewsRemoteMediator(remoteDataSource, newsDatabase),
      pagingSourceFactory = { newsDatabase.articleDao().getAllArticles() }
    ).flow
      .map { pagingData ->
        pagingData.filter { it.urlToImage?.isNotEmpty() == true }
          .map { it.toArticle() }
      }
  }
}
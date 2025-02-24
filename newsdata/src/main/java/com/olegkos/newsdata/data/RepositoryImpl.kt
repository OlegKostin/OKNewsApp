package com.olegkos.newsdata.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
  private val newsPagingSource: NewsPagingSource
) : Repository {

  override fun getNews(pageSize: Int): Flow<PagingData<Article>> {
    return Pager(
      config = PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = false,
        prefetchDistance = pageSize / 2
      ),
      pagingSourceFactory = { newsPagingSource }
    ).flow
  }
}
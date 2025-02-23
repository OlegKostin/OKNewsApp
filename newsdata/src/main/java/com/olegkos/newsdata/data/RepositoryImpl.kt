package com.olegkos.newsdata.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsapi.utils.ApiResult
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles
import com.olegkos.newsdata.utils.toTotalResultArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) : Repository {

  override fun getNews(pageSize: Int): Flow<RequestResult<PagingData<Article>>> {
    return flow {
      emit(RequestResult.InProgress())
      try {
        val pager = Pager(
          config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
            prefetchDistance = pageSize / 2
          ),
          pagingSourceFactory = { NewsPagingSource(remoteDataSource) }
        ).flow
        pager.collect {
          emit(RequestResult.Success(it))
        }
      } catch (e: Exception) {
        emit(RequestResult.Error(data = null, error = e))
      }
    }
  }
}
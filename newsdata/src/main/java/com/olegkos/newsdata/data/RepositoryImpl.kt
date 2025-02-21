package com.olegkos.newsdata.data

import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsapi.utils.ApiResult
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles
import com.olegkos.newsdata.utils.toTotalResultArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) : Repository {

  // Маппим результат из RemoteDataSource
  override fun getNews(): Flow<RequestResult<TotalResultArticles<Article>>> {
    return flow {

    emit(RequestResult.InProgress())

      val apiResult = remoteDataSource.getNews()

      // Маппим ResponseDTO в TotalResultArticles
      when (apiResult) {
        is ApiResult.Success -> {
          // Маппим данные из ApiResult.Success
          val responseDTO = apiResult.data
          val totalResultArticles = responseDTO.toTotalResultArticles()
          emit(RequestResult.Success(totalResultArticles))
        }

        is ApiResult.Error -> {
          // Маппим ошибку из ApiResult.Error
          emit(RequestResult.Error(data = null, error = apiResult.exception))
        }

        ApiResult.Loading -> emit(RequestResult.InProgress())
      }
    }
  }
}
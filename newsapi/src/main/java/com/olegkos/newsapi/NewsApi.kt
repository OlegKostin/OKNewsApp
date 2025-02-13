package com.olegkos.newsapi

import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Api Details https://newsapi.org/docs/endpoints/everything
interface NewsApi {

  @GET("everything")
  suspend fun getAll(
    @Query("apiKey") key: String = BuildConfig.API_KEY,
    @Query("q") query: String,
  ): Response<ResponseDTO<ArticleDTO>>
}




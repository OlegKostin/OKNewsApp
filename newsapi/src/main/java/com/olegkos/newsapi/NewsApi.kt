package com.olegkos.newsapi

import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Api Details https://newsapi.org/docs/endpoints/everything
interface NewsApi {

  @GET("top-headlines")
  suspend fun getTopNews(
    @Query("apiKey") key: String = BuildConfig.API_KEY,
    @Query("country") country: String = "us",
    @Query("pageSize") pageSize: Int,
    @Query("page") page: Int,

  ): Response<ResponseDTO<ArticleDTO>>
}




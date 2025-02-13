package com.olegkos.newsapi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

//Api Details https://newsapi.org/docs/endpoints/everything
interface NewsApi {

  @GET("everything")
  suspend  fun getAll(
    @Query("apiKey") key: String = BuildConfig.API_KEY,
    @Query("q") query: String? = null,
    ): Response<ResponseDTO<ArticleDTO>>
}




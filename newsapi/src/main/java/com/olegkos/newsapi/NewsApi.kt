package com.olegkos.newsapi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

//Api Details https://newsapi.org/docs/endpoints/everything
interface NewsApi {

  @GET("everything")
  suspend  fun getAll(
    @Query("apiKey") key: String = BuildConfig.API_KEY,
    @Query("q") query: String? = null,
    ): ResponseDTO<ArticleDTO>
}

object RetrofitInstance {
  val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }
  // OkHttpClient с перехватчиком
  private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

  private const val BASE_URL = "https://newsapi.org/v2/"
  val json = Json { ignoreUnknownKeys = true }
  val converterFactory = json.asConverterFactory("application/json".toMediaType())
  val api: NewsApi by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .build()
      .create(NewsApi::class.java)
  }
}


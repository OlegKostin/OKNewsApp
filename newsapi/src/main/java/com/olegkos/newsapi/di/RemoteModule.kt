package com.olegkos.newsapi.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olegkos.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

  @Provides
  @Singleton
  fun provideBaseUrl(): String {
    return "https://newsapi.org/v2/"
  }


  // Предоставляем HttpLoggingInterceptor
  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  // Предоставляем OkHttpClient с перехватчиком
  @Provides
  @Singleton
  fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .readTimeout(15, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()
  }


  @Provides
  @Singleton
  fun provideJson(): Json {
    return Json { ignoreUnknownKeys = true }
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    json: Json
  ): Retrofit {
    val mediaType = "application/json".toMediaType()
    val converterFactory = json.asConverterFactory(mediaType)
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .build()
  }


  @Provides
  @Singleton
  fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }


}
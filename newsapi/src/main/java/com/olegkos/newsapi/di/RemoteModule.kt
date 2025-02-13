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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

  private const val BASE_URL = "https://newsapi.org/v2/"

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
      .build()
  }

  // Предоставляем Json для сериализации
  @Provides
  @Singleton
  fun provideJson(): Json {
    return Json { ignoreUnknownKeys = true }
  }

  // Предоставляем Retrofit
  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    json: Json
  ): Retrofit {
    val mediaType = "application/json".toMediaType()
    val converterFactory = json.asConverterFactory(mediaType)
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .build()
  }

  // Предоставляем NewsApi
  @Provides
  @Singleton
  fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }
}
package com.olegkos.newsapi.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olegkos.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
object RemoteModule {

  @Provides

  fun provideBaseUrl(): String {
    return "https://newsapi.org/v2/"
  }


  // Предоставляем HttpLoggingInterceptor
//  @Provides
//  @Singleton
//  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//    return HttpLoggingInterceptor().apply {
//      level = HttpLoggingInterceptor.Level.BODY
//    }
//  }

  // Предоставляем OkHttpClient с перехватчиком
  @Provides

  fun provideOkHttpClient(
  //  loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
 //     .addInterceptor(loggingInterceptor)
      .build()
  }

  // Предоставляем Json для сериализации
  @Provides

  fun provideJson(): Json {
    return Json { ignoreUnknownKeys = true }
  }

  // Предоставляем Retrofit
  @Provides

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

  // Предоставляем NewsApi
  @Provides

  fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }


}
package com.olegkos.newsdata.di

import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsdata.data.RepositoryImpl
import com.olegkos.newsdata.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
  @Provides
  fun provideRepositoryImpl(remoteDataSource: RemoteDataSource): Repository {
    return RepositoryImpl(
      remoteDataSource = remoteDataSource
    )
  }

}
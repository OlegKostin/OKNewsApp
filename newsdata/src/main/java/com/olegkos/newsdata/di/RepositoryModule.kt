package com.olegkos.newsdata.di

import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsdata.data.RepositoryImpl
import com.olegkos.newsdata.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideRepository(remoteDataSource: RemoteDataSource): Repository {
    return RepositoryImpl(remoteDataSource)
  }
}
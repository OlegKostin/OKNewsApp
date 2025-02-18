package com.olegkos.newsdata.di

import com.olegkos.newsapi.RemoteDataSource
import com.olegkos.newsdata.data.RepositoryImpl
import com.olegkos.newsdata.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {

  @Binds
  fun bindRepositoryImpl_to_Repository(repositoryImpl: RepositoryImpl): Repository
}
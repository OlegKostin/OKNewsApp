package com.olegkos.news_database.di

import android.content.Context
import androidx.room.Room
import com.olegkos.news_database.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context: Context
  ): NewsDataBase {
    return Room.databaseBuilder(
      context,
      NewsDataBase::class.java,
      "news_data_base"
    ).build()
  }

}
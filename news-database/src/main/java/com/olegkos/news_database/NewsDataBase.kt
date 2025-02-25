package com.olegkos.news_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olegkos.news_database.dao.ArticleDao
import com.olegkos.news_database.dao.RemoteKeysDao
import com.olegkos.news_database.models.ArticleEntity
import com.olegkos.news_database.models.RemoteKeys

@Database(entities = [ArticleEntity::class, RemoteKeys::class], version = 1)
abstract class NewsDataBase : RoomDatabase() {

  abstract fun articleDao(): ArticleDao
  abstract fun remoteKeysDao(): RemoteKeysDao
}
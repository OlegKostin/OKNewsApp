package com.olegkos.news_database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olegkos.news_database.models.ArticleEntity

@Dao
interface ArticleDao {
  @Query("SELECT * FROM articles")
  fun getAllArticles(): PagingSource<Int, ArticleEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addArticles(article: List<ArticleEntity>)

  @Query("DELETE FROM articles")
  suspend fun deleteAllArticles()


}
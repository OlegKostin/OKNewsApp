package com.olegkos.news_database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val author: String?,
  val title: String,
  val description: String?,
  val publishedAt: String?,
  val url: String?,
  val urlToImage: String?,
)
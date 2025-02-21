package com.olegkos.newsdata.models

data class Article(
  val author: String?,
  val title: String,
  val description: String?,
  val publishedAt: String?,
  val url: String?,
  val urlToImage: String,
)
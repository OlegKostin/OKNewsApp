package com.olegkos.newsdata.models

data class TotalResultArticles<E>(
  val totalResults: Int,
  val articles: List<E>,
)
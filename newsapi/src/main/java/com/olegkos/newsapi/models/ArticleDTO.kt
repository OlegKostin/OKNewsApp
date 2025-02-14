package com.olegkos.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
  @SerialName("author") val author: String?,
  @SerialName("title") val title: String,
//    val description: String,
//    val publishedAt: String,
//    val source: Source,
//    val title: String,
//    val url: String,
//    val urlToImage: String
)
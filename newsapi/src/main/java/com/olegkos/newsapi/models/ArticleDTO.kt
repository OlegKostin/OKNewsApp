package com.olegkos.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
  @SerialName("author") val author: String?,
  @SerialName("title") val title: String,
  @SerialName("description") val description: String?,
  @SerialName("publishedAt") val publishedAt: String?,
  //@SerialName("source")val source: SourceDTO,
  @SerialName("url") val url: String?,
  @SerialName("urlToImage") val urlToImage: String?,
)
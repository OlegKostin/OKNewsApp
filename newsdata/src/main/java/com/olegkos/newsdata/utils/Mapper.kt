package com.olegkos.newsdata.utils

import com.olegkos.news_database.models.ArticleEntity
import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.TotalResultArticles


fun <E> ResponseDTO<E>.toTotalResultArticles(): TotalResultArticles<Article> {
  return TotalResultArticles(
    totalResults = this.totalResults,
    articles = this.articles
      .filterIsInstance<ArticleDTO>()
      .filter { it.urlToImage != null }
      .map { article ->
        Article(
          author = article.author,
          title = article.title,
          description = article.description,
          publishedAt = article.publishedAt,
          url = article.url,
          urlToImage = article.urlToImage!!,
        )
      }
  )
}

fun ArticleDTO.toArticleEntity(): ArticleEntity {
  return ArticleEntity(
    author = this.author,
    title = this.title,
    description = this.description,
    publishedAt = this.publishedAt,
    url = this.url,
    urlToImage = this.urlToImage
  )
}

fun ArticleEntity.toArticle(): Article {
  return Article(
    author = this.author,
    title = this.title,
    description = this.description,
    publishedAt = this.publishedAt,
    url = this.url,
    urlToImage = this.urlToImage ?: ""
  )
}
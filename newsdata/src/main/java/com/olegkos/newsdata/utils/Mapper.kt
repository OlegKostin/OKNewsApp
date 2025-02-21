package com.olegkos.newsdata.utils

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
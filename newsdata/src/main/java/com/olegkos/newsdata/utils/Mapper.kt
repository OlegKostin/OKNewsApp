package com.olegkos.newsdata.utils

import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import com.olegkos.newsapi.utils.ApiResult
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles

fun <T : Any> ApiResult<T>.toRequestResult(): RequestResult<T> {
  return when (this) {
    is ApiResult.Success -> {
      RequestResult.Success(data = this.data)
    }

    is ApiResult.Error -> {
      RequestResult.Error(data = null, error = this.exception)
    }

    ApiResult.Loading -> {
      RequestResult.InProgress()
    }
  }
}

fun <E> ResponseDTO<E>.toTotalResultArticles(): TotalResultArticles<Article> {
  return TotalResultArticles(
    totalResults = this.totalResults,
    articles = this.articles.map { article ->
      when (article) {
        is ArticleDTO -> Article(
          author = article.author,
          title = article.title,
          description = article.description,
          publishedAt = article.publishedAt,
          url = article.url,
          urlToImage = article.urlToImage,
        )

        else -> throw IllegalArgumentException("Unknown article type")
      }
    }
  )
}
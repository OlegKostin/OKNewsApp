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
      // В случае успеха возвращаем RequestResult.Success
      RequestResult.Success(data = this.data)
    }

    is ApiResult.Error -> {
      // В случае ошибки, передаем ошибку в RequestResult.Error
      RequestResult.Error(data = null, error = this.exception)
    }

    ApiResult.Loading -> {
      // В случае загрузки, возвращаем RequestResult.InProgress
      RequestResult.InProgress()
    }
  }
}

fun <E> ResponseDTO<E>.toTotalResultArticles(): TotalResultArticles<Article> {
  return TotalResultArticles(
    totalResults = this.totalResults,
    articles = this.articles.map { article ->
      // Маппим каждый элемент в списке articles в Article
      when (article) {
        is ArticleDTO -> Article(
          author = article.author ?: "no author",
          title = article.title,
        ) // Конвертируем ArticleDTO в Article
        else -> throw IllegalArgumentException("Unknown article type")
      }
    }
  )
}
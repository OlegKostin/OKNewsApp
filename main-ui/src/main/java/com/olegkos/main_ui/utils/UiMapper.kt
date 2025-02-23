package com.olegkos.main_ui.utils

import com.olegkos.main_ui.NewsUiState
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles

//fun RequestResult<TotalResultArticles<Article>>.toNewsUiState(): NewsUiState {
//  return when (this) {
//    is RequestResult.InProgress -> {
//      NewsUiState.Loading(data?.articles?.map { it.toArticleUI() })
//    }
//
//    is RequestResult.Success -> {
//      NewsUiState.Success(data.articles.map { it.toArticleUI() })
//    }
//
//    is RequestResult.Error -> {
//      NewsUiState.Error(data?.articles?.map { it.toArticleUI() })
//    }
//  }
//}

fun Article.toArticleUI(): ArticleUI {
  return ArticleUI(
    author = this.author ?: "",
    title = this.title,
    description = this.description ?: "",
    publishedAt = this.publishedAt ?: "",
    url = this.url ?: "",
    urlToImage = this.urlToImage ?: ""
  )
}
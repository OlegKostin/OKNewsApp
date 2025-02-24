package com.olegkos.main_ui.utils

import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.newsdata.models.Article


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
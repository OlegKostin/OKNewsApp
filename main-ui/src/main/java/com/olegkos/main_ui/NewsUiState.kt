package com.olegkos.main_ui

import com.olegkos.main_ui.model.ArticleUI

sealed class NewsUiState(open val articles: List<ArticleUI>?) {

  data object None : NewsUiState(articles = null)

  class Loading(articles: List<ArticleUI>? = null) : NewsUiState(articles)

  class Error(articles: List<ArticleUI>? = null) : NewsUiState(articles)

  class Success(override val articles: List<ArticleUI>) : NewsUiState(articles)
}

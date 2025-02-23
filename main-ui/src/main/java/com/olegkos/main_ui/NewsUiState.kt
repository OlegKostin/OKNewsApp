package com.olegkos.main_ui

import androidx.paging.PagingData
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.newsdata.models.Article

sealed class NewsUiState {
  object None : NewsUiState()
  object Loading : NewsUiState()
  data class Success(val data: PagingData<Article>) : NewsUiState()
  data class Error(val message: String) : NewsUiState()
}

package com.olegkos.main_ui

import androidx.paging.PagingData
import com.olegkos.main_ui.model.ArticleUI

sealed class NewsUiState {
  object None : NewsUiState()
  object Loading : NewsUiState()
  data class Success(val data: PagingData<ArticleUI>) : NewsUiState()
  data class Error(val message: String) : NewsUiState()
}

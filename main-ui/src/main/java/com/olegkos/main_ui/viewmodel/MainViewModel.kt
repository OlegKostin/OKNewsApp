package com.olegkos.main_ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {
  private val _uiState: MutableStateFlow<HomeUiState> =
    MutableStateFlow(
      HomeUiState(
        article = emptyList(),
        error = ""
      )
    )
  var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun get() = viewModelScope.launch {

    repository.getNews("android").map {
      it.toHomeUiState()
    }.collect {
      _uiState.value = _uiState.value.copy(article = it.article, error = it.error)
    }
  }
}


data class HomeUiState(
  val error: String,
  val article: List<Article> = emptyList(),
)

fun RequestResult<TotalResultArticles<Article>>.toHomeUiState(): HomeUiState {
  return when (this) {
    is RequestResult.InProgress -> {
      HomeUiState(article = emptyList(), error = "")
    }

    is RequestResult.Success -> {

      HomeUiState(error = "", article = this.data.articles)
    }

    is RequestResult.Error -> {
      Log.d("TAG", this.error?.message!!)
      HomeUiState(error = this.error?.message ?: "Неизвестная ошибка")
    }
  }
}
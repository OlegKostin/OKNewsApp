package com.olegkos.oknewsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.newsapi.RetrofitInstance
import com.olegkos.newsapi.models.ArticleDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
  private val _uiState: MutableStateFlow<HomeUiState> =
    MutableStateFlow(
      HomeUiState(
        article = ArticleDTO(author = "no")
      )
    )
  var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  init {
    get()
  }

  fun get() = viewModelScope.launch {

    val response = RetrofitInstance.api.getAll(query = "android")
    _uiState.value =
      HomeUiState(article = response.articles.firstOrNull() ?: ArticleDTO(author = "No author"))

  }
}


data class HomeUiState(

  val article: ArticleDTO,
)
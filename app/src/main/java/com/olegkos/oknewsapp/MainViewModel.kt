package com.olegkos.oknewsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.newsapi.NewsApi
import com.olegkos.newsapi.models.ArticleDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val newsApi: NewsApi) : ViewModel() {
  private val _uiState: MutableStateFlow<HomeUiState> =
    MutableStateFlow(
      HomeUiState(
        article = emptyList()
      )
    )
  var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun get() = viewModelScope.launch {

    val response = newsApi.getAll(query = "android")
    _uiState.update { currentState ->
      currentState.copy(article = response.body()?.articles!!)
    }

  }
}


data class HomeUiState(

  val article: List<ArticleDTO>,
)
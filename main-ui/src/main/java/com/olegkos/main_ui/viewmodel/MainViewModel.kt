package com.olegkos.main_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.olegkos.main_ui.NewsUiState
import com.olegkos.newsdata.domain.Repository
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.transform


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

  // StateFlow для хранения состояния загрузки (без PagingData)
  private val _state = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
  val state: StateFlow<NewsUiState> = _state

  // Отдельный поток с PagingData для списка
  val newsFlow: Flow<PagingData<Article>> = repository.getNews(pageSize = 20)
    .transform { result ->
      when (result) {
        is RequestResult.Success -> emit(result.data) // Эмитим данные PagingData
        is RequestResult.Error -> _state.value = NewsUiState.Error(result.error?.message ?: "Unknown error")
        is RequestResult.InProgress -> _state.value = NewsUiState.Loading
      }
    }
    .cachedIn(viewModelScope) // Кешируем

  init {
    viewModelScope.launch {
      newsFlow.collectLatest {
        _state.value = NewsUiState.Success(it)
      }
    }
  }
}
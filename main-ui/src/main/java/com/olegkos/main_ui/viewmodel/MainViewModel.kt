package com.olegkos.main_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.main_ui.NewsUiState
import com.olegkos.main_ui.utils.toNewsUiState
import com.olegkos.newsdata.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
  public val state: StateFlow<NewsUiState> =
    repository.getNews()
      .map { it.toNewsUiState() }
      .stateIn(viewModelScope, SharingStarted.Lazily, NewsUiState.None)
}




package com.olegkos.main_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.main_ui.utils.toArticleUI
import com.olegkos.newsdata.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

  val newsFlow: StateFlow<PagingData<ArticleUI>> = repository.getNews(pageSize = 20)
    .map { pagingData -> pagingData.map { it.toArticleUI() } }
    .cachedIn(viewModelScope)
    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}

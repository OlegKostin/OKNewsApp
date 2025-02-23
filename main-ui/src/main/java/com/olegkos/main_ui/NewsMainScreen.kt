package com.olegkos.main_ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.olegkos.main_ui.components.ProgressIndicator
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.main_ui.viewmodel.MainViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.olegkos.newsdata.models.Article

@Composable
fun MainScreen() {
  NewsMainScreen(viewModel = hiltViewModel())
}

@Composable
internal fun NewsMainScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier,
) {
  val state: NewsUiState by viewModel.state.collectAsState()
  val news = viewModel.newsFlow.collectAsLazyPagingItems()
  val currentState = state
  Scaffold(

  ) { padding ->
    Column(modifier = modifier.padding(padding)) {
      when (currentState) {

        is NewsUiState.Error -> {}//TODO
        is NewsUiState.Loading -> ProgressIndicator(state = currentState)
        NewsUiState.None -> Unit
        is NewsUiState.Success -> {}//TODO
      }
    }
  }
}

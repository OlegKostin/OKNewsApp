package com.olegkos.main_ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.olegkos.main_ui.components.ProgressIndicator
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.main_ui.viewmodel.MainViewModel

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
  val currentState = state
  Scaffold(

  ) { padding ->
    Column(modifier = modifier.padding(padding)) {
      when (currentState) {

        is NewsUiState.Error -> {}//TODO
        is NewsUiState.Loading -> ProgressIndicator(state = currentState)
        NewsUiState.None -> Unit
        is NewsUiState.Success -> ArticleLi(articleState = currentState)
      }
    }
  }
}

@Composable
internal fun ArticleLi(
  articleState: NewsUiState.Success,
  modifier: Modifier = Modifier
) {
  ArticleList(articles = articleState.articles, modifier)
}

@Composable
internal fun ArticleList(
  articles: List<ArticleUI>,
  modifier: Modifier = Modifier
) {
  val pagerState = rememberPagerState(pageCount = {
    articles.size
  })
  HorizontalPager(state = pagerState) { page ->
    // Our page content
    Article(articles[page])
  }
  LazyColumn(modifier) {

  }
}


@Composable
internal fun Article(
  article: ArticleUI,
  modifier: Modifier = Modifier
) {

  Box(
    modifier
      .padding(bottom = 4.dp)
      .fillMaxSize()) {
    Log.d("TAG", article.urlToImage)
    article.urlToImage?.let { imageUrl ->
      var isImageVisible by remember { mutableStateOf(true) }
      if (isImageVisible) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
          onState = { state ->
            if (state is AsyncImagePainter.State.Error) {
              val error = state.result.throwable
              Log.d("TAG", "Error loading image: ${error?.message}")
            }
          },
          contentDescription = "",
          contentScale = ContentScale.Crop,

          modifier = Modifier.fillMaxSize()
        )
      }
    }


  }
}

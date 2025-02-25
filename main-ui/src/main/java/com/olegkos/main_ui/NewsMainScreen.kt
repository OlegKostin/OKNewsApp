package com.olegkos.main_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.olegkos.main_ui.model.ArticleUI
import com.olegkos.main_ui.viewmodel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
  val news = viewModel.newsFlow.collectAsLazyPagingItems()

  Column(modifier = Modifier.fillMaxSize()) {
    when (news.loadState.refresh) { // Первая загрузка
      is LoadState.Loading -> CircularProgressIndicator()
      is LoadState.Error -> {
        val error = (news.loadState.refresh as LoadState.Error).error
        // Теперь вы можете обработать ошибку, например:
        Text(
          text = "Ошибка загрузки: ${error.message}" +
              "загрузки: ${error.cause}",
          color = Color.Red,
          modifier = Modifier.align(
            alignment = Alignment.Start
          )
        )
      }

      is LoadState.NotLoading -> NewsList(news)
    }
  }
}

@Composable
fun NewsList(news: LazyPagingItems<ArticleUI>) {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(news.itemCount) { index ->

      val article = news[index]
      article?.let {
        Column {
          Text(text = it.publishedAt)
          AsyncImage(
            model = it.urlToImage,
            contentDescription = "Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
          )
        }
      }
    }
  }
}
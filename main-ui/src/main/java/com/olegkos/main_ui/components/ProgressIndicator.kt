package com.olegkos.main_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olegkos.main_ui.NewsUiState

@Composable
fun ProgressIndicator(
  state: NewsUiState.Loading,
  modifier: Modifier = Modifier,
) {
  Column {
    Box(
      modifier
        .fillMaxWidth()
        .padding(8.dp),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator()
    }

    val articles = state.articles
    if (articles != null) {
      {} //TODO
    }
  }
}

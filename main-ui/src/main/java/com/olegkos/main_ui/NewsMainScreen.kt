package com.olegkos.main_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.olegkos.main_ui.viewmodel.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
  NewsMainScreen(viewModel = hiltViewModel(), modifier = modifier)
}

@Composable
internal fun NewsMainScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    val state by viewModel.uiState.collectAsState()
    Button(onClick = { viewModel.get() }) {
      Text(text = if (state.error.isNotEmpty()) state.error else "Загрузка...")
    }

    LazyColumn {

      items(state.article) {
        Text(
          text = it.author,
          style = MaterialTheme.typography.displayMedium
        )
      }
    }

  }

}
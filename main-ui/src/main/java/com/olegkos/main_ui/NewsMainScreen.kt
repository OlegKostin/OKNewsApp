package com.olegkos.main_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.olegkos.main_ui.components.ProgressIndicator
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
        is NewsUiState.Success -> {}//TODO
      }
    }
  }
}


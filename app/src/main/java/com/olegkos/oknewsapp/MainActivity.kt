package com.olegkos.oknewsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.olegkos.oknewsapp.ui.theme.OKNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()



    setContent {
      val viewModel: MainViewModel = hiltViewModel()
      val state by viewModel.uiState.collectAsState()

      OKNewsAppTheme {
        Column(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
    }
  }
}



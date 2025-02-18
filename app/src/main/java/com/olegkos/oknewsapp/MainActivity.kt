package com.olegkos.oknewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.olegkos.main_ui.MainScreen
import com.olegkos.main_ui.viewmodel.MainViewModel
import com.olegkos.oknewsapp.ui.theme.OKNewsAppTheme
import javax.inject.Inject


class MainActivity : ComponentActivity() {
  @Inject
  lateinit var factory: ViewModelProvider.Factory

  @Inject
  lateinit var viewModel: MainViewModel
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    appComponent.inject(this)
    setContent {
      OKNewsAppTheme {
        MainScreen(
          factory = factory,
          viewModel = viewModel
        )
      }
    }
  }
}



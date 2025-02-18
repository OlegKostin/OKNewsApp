package com.olegkos.oknewsapp.di

import com.olegkos.main_ui.di.ViewModelModule
import com.olegkos.main_ui.viewmodel.MainViewModel
import com.olegkos.newsapi.di.RemoteModule
import com.olegkos.newsdata.di.RepositoryModule
import com.olegkos.oknewsapp.MainActivity
import dagger.Component
import javax.inject.Singleton


@Component(
  modules = [
    AppModule::class,
    RemoteModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
  ]
)
interface AppComponent {
fun viewModel(mainViewModel: MainViewModel)
  fun inject(activity: MainActivity)
}
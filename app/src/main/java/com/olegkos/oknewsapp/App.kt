package com.olegkos.oknewsapp

import android.app.Application
import android.content.Context
import com.olegkos.oknewsapp.di.AppComponent
import com.olegkos.oknewsapp.di.DaggerAppComponent


class App : Application() {
  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder().build()
  }
}
val Context.appComponent: AppComponent
  get() = when (this) {
    is App -> appComponent
    else -> applicationContext.appComponent
  }
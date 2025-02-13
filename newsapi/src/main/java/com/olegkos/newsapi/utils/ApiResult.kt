package com.olegkos.newsapi.utils

sealed class ApiResult<out T> {
  data class Success<out T>(val data: T) : ApiResult<T>()
  data class Error(val exception: ApiException) : ApiResult<Nothing>()
  object Loading : ApiResult<Nothing>()
}

data class ApiException(val code: Int, override val message: String) : Exception(message)
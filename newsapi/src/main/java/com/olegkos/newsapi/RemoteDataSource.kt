package com.olegkos.newsapi

import com.olegkos.newsapi.models.ArticleDTO
import com.olegkos.newsapi.models.ResponseDTO
import com.olegkos.newsapi.utils.ApiException
import com.olegkos.newsapi.utils.ApiResult
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val newsApi: NewsApi) {

  suspend fun getNews(query: String): ApiResult<ResponseDTO<ArticleDTO>> {
    return try {
      val response = newsApi.getTopNews()

      if (response.isSuccessful) {
        response.body()?.let {
          // Возвращаем успешный результат
          return ApiResult.Success(it)
        } ?: run {
          // Тело пустое, возвращаем ошибку
          return ApiResult.Error(ApiException(204, "Тело ответа пустое"))
        }
      } else {
        // Обрабатываем ошибки по статусам
        return when (response.code()) {
          400 -> ApiResult.Error(
            ApiException(
              400,
              "Неверный запрос. Возможно, отсутствует параметр или он неправильно настроен"
            )
          )

          401 -> ApiResult.Error(
            ApiException(
              401,
              "Неавторизованный запрос. Проверьте ваш API ключ"
            )
          )

          429 -> ApiResult.Error(ApiException(429, "Слишком много запросов. Попробуйте позже"))
          500 -> ApiResult.Error(ApiException(500, "Ошибка сервера. Попробуйте снова позже"))
          else -> ApiResult.Error(
            ApiException(
              response.code(),
              "Неизвестная ошибка. Код: ${response.code()}"
            )
          )
        }
      }
    } catch (e: Exception) {
      // Если произошла ошибка, возвращаем её
      return ApiResult.Error(ApiException(0, "Ошибка сети или другое исключение: ${e.message}"))
    }
  }
}
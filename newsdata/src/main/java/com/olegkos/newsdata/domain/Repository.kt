package com.olegkos.newsdata.domain

import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles
import kotlinx.coroutines.flow.Flow

interface Repository {

  fun getNews(): Flow<RequestResult<TotalResultArticles<Article>>>

}
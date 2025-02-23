package com.olegkos.newsdata.domain

import androidx.paging.PagingData
import com.olegkos.newsdata.models.Article
import com.olegkos.newsdata.models.RequestResult
import com.olegkos.newsdata.models.TotalResultArticles
import kotlinx.coroutines.flow.Flow

interface Repository {

  fun getNews(pageSize: Int): Flow<RequestResult<PagingData<Article>>>

}
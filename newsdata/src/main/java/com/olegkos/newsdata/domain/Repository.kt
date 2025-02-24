package com.olegkos.newsdata.domain

import androidx.paging.PagingData
import com.olegkos.newsdata.models.Article
import kotlinx.coroutines.flow.Flow

interface Repository {

  fun getNews(pageSize: Int): Flow<PagingData<Article>>

}
package com.olegkos.newsapi.models

import kotlinx.serialization.Serializable

@Serializable
data class SourceDTO(
  val id: String,
  val name: String
)
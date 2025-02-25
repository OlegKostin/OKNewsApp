package com.olegkos.news_database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
  @PrimaryKey(autoGenerate = false)
  val id: String,
  val prevPage: Int?,
  val nextPage: Int?,
)
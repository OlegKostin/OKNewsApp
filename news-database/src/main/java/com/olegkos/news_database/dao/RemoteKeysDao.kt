package com.olegkos.news_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olegkos.news_database.models.RemoteKeys

@Dao
interface RemoteKeysDao {
  @Query("SELECT * FROM remote_keys WHERE id =:id")
  suspend fun getRemoteKeys(id: String): RemoteKeys

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

  @Query("DELETE FROM remote_keys")
  suspend fun deleteAllRemoteKeys()

}
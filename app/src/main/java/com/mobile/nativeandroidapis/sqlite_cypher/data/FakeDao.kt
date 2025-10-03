package com.mobile.nativeandroidapis.sqlite_cypher.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fakeEntity: FakeEntity)

    @Query("SELECT * FROM FakeEntity")
    suspend fun getFakeEntities():List<FakeEntity>

}

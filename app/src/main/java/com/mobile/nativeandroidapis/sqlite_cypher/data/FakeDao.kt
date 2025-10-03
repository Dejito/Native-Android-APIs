package com.mobile.nativeandroidapis.sqlite_cypher.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fakeEntity: FakeEntity)

    @Query("SELECT * FROM FakeEntity")
    suspend fun getFakeEntities(): List<FakeEntity>

    @Update
    suspend fun update(fakeEntity: FakeEntity)

    @Delete
    suspend fun delete(fakeEntity: FakeEntity)
}


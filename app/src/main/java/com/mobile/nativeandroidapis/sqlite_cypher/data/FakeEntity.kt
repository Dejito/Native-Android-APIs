package com.mobile.nativeandroidapis.sqlite_cypher.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FakeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
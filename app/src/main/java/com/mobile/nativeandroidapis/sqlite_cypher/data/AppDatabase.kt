package com.mobile.nativeandroidapis.sqlite_cypher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [FakeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val passPhrase: ByteArray = SQLiteDatabase.getBytes("native-app-db-key1237792001".toCharArray())
        val factory = SupportFactory(passPhrase)

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "native_feat_db")
                .openHelperFactory(factory = factory)
                .build()
        }
    }

    abstract fun getFakeDao():FakeDao

}
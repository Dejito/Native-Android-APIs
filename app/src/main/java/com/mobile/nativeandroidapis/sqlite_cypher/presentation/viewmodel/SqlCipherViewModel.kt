package com.mobile.nativeandroidapis.sqlite_cypher.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.nativeandroidapis.sqlite_cypher.data.AppDatabase
import com.mobile.nativeandroidapis.sqlite_cypher.data.FakeEntity
import kotlinx.coroutines.launch

class SqlCipherViewModel(private val db: AppDatabase) : ViewModel() {

    private val _items = mutableStateListOf<FakeEntity>()
    val items: List<FakeEntity> get() = _items

    init {
        retrieveItems()
    }

    fun insertItem(name: String) {
        viewModelScope.launch {
            db.getFakeDao().insert(FakeEntity(name = name))
            retrieveItems()
        }
    }

    fun retrieveItems() {
        viewModelScope.launch {
            val result = db.getFakeDao().getFakeEntities()
            _items.clear()
            _items.addAll(result)
        }
    }
}

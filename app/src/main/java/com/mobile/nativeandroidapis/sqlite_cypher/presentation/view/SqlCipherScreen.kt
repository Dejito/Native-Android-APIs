package com.mobile.nativeandroidapis.sqlite_cypher.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.sqlite_cypher.presentation.viewmodel.SqlCipherViewModel
import com.mobile.nativeandroidapis.sqlite_cypher.data.AppDatabase
import com.mobile.nativeandroidapis.sqlite_cypher.data.FakeEntity
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar


@SuppressLint("NewApi")
@Composable
fun SqlCipherScreen(
    db: AppDatabase,
    navigation: Navigator
) {
    val viewModel: SqlCipherViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SqlCipherViewModel(db) as T
            }
        }
    )

    var text by remember { mutableStateOf("") }
    var editText by remember { mutableStateOf("") }
    var editingItem by remember { mutableStateOf<FakeEntity?>(null) }

    Scaffold(
      topBar = { CustomAppBar(title = "SQLCipher") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                if (text.isNotEmpty()) {
                    viewModel.insertItem(text)
                    text = ""
                }
            }) {
                Text("Insert into DB")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Saved Entries:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(viewModel.items) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("ID: ${item.id}, Name: ${item.name}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                Button(
                                    onClick = {
                                        editingItem = item
                                        editText = item.name
                                    },
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Edit")
                                }

                                Button(
                                    onClick = { viewModel.deleteItem(item) },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Edit Dialog
        if (editingItem != null) {
            AlertDialog(
                onDismissRequest = { editingItem = null },
                confirmButton = {
                    Button(onClick = {
                        editingItem?.let { viewModel.updateItem(it, editText) }
                        editingItem = null
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { editingItem = null }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Edit Item") },
                text = {
                    OutlinedTextField(
                        value = editText,
                        onValueChange = { editText = it },
                        label = { Text("New Name") }
                    )
                }
            )
        }
    }


}

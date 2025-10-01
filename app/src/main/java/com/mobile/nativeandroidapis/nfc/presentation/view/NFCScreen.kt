package com.mobile.nativeandroidapis.nfc.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.ui.screens.TitleText
import org.koin.androidx.compose.koinViewModel

@Composable
fun NfcScreen(nfcViewModel: NFCViewModel = koinViewModel()) {

    val uiState by nfcViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { CustomAppBar(title = "NFC Screen") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TitleText("NFC Available: ${uiState.isAvailable}")
            TitleText("NFC Enabled: ${uiState.isEnabled}")
            TitleText("Mode: ${if (uiState.isWriting) "Write" else "Read"}")

            Spacer(Modifier.height(8.dp))

            Button(onClick = { nfcViewModel.toggleWritingMode() }) {
                Text(if (uiState.isWriting) "Switch to Read" else "Switch to Write")
            }

            if (uiState.isWriting) {
                OutlinedTextField(
                    value = uiState.messageToWrite,
                    onValueChange = { nfcViewModel.updateMessageToWrite(it) },
                    label = { Text("Message to Write") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))

            SelectionContainer {
                Text("Records:\n${uiState.records}")
            }
        }

    }

}

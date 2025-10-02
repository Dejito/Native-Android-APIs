package com.mobile.nativeandroidapis.nfc.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.ui.screens.TitleText
import org.koin.androidx.compose.koinViewModel

@Composable
fun NfcScreen(
    navigator: Navigator,
    nfcViewModel: NFCViewModel = koinViewModel()
) {
    val uiState = nfcViewModel.nfcState.collectAsState().value

    Scaffold(
        topBar = { CustomAppBar(title = "NFC Screen", onClick = { navigator.navigateBack() }) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (!uiState.isAvailable) {
                    Text(text = "Sorry! Your phone does not support NFC.")
                }

                if (uiState.isAvailable) {
                    // --- NFC Status ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "NFC status",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(
                                onClick = {},
                                label = { Text(text = "Available") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "NFC is available"
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.background
                                )
                            )

                            AssistChip(
                                onClick = {},
                                label = { Text(text = if (uiState.isEnabled) "Enabled" else "Disabled") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (uiState.isEnabled) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = if (uiState.isEnabled) "NFC is enabled" else "NFC is not enabled",
                                        tint = if (uiState.isEnabled)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onErrorContainer
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (uiState.isEnabled)
                                        MaterialTheme.colorScheme.background
                                    else
                                        MaterialTheme.colorScheme.errorContainer
                                )
                            )
                        }
                    }

                    // --- Mode Section ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mode: ${if (uiState.isWriting) "Write" else "Read"}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            Button(onClick = { nfcViewModel.toggleWritingMode() }) {
                                Text(text = if (uiState.isWriting) "Read" else "Write")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { nfcViewModel.reset() }) {
                                Text(text = "Reset")
                            }
                        }
                    }

                    // --- Message Input ---
                    if (uiState.isWriting) {
                        OutlinedTextField(
                            value = uiState.messageToWrite,
                            onValueChange = { nfcViewModel.updateMessageToWrite(it) },
                            label = { Text("Payload to write") },
                            maxLines = 3,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Divider(modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))

                    // --- Records Section ---
                    SelectionContainer {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Text(text = getSection("TechList", uiState.techList))
                            Divider(modifier = Modifier.padding(vertical = 4.dp))
                            Text(text = getSection("Records", uiState.records))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun getSection(title: String, content: String): String {
    val separator = "-".repeat(50)
    return "$title:\n$separator\n$content\n$separator\n"
}

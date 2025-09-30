package com.mobile.nativeandroidapis.nfc.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.nativeandroidapis.nfc.data.NfcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class NFCViewModel(
    private val repository: NfcRepository = NfcRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(NfcUiState())
    val state: StateFlow<NfcUiState> = _state

    fun updateIsAvailable(value: Boolean) {
        _state.value = _state.value.copy(isAvailable = value)
    }

    fun updateIsEnabled(value: Boolean) {
        _state.value = _state.value.copy(isEnabled = value)
    }

    fun toggleWritingMode() {
        _state.value = _state.value.copy(isWriting = !_state.value.isWriting)
    }

    fun updateMessageToWrite(value: String) {
        _state.value = _state.value.copy(messageToWrite = value)
    }

    fun readTag(tag: android.nfc.Tag?, messages: Array<android.nfc.NdefMessage>?) {
        viewModelScope.launch {
            val tagData = repository.readTag(tag, messages)
            tagData?.let {
                _state.value = _state.value.copy(
                    lastTag = it,
                    techList = it.techList ?: "",
                    records = it.payload
                )
            }
        }
    }

    fun writeTag(tag: android.nfc.Tag?) {
        viewModelScope.launch {
            val success = repository.writeTag(tag, _state.value.messageToWrite)
            _state.value = _state.value.copy(
                records = if (success) "Write successful" else "Write failed"
            )
        }
    }

    fun reset() {
        _state.value = NfcUiState()
    }
}

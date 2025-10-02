package com.mobile.nativeandroidapis.nfc.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.nativeandroidapis.nfc.data.NfcRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class NFCViewModel(
    private val repository: NfcRepository = NfcRepository()
) : ViewModel() {

    private val _nfcState = MutableStateFlow(NfcUiState())
    val nfcState: StateFlow<NfcUiState> = _nfcState

    fun setNfcSupportedByDeviceCheck(value: Boolean) {
        println("nfc viewmodel state is....$value")
        Log.d("NFCViewModel", "isAvailable = $value")
        _nfcState.value = _nfcState.value.copy(isAvailable = value)
    }


    fun setNfcEnabledOnDeviceCheck(value: Boolean) {
        _nfcState.value = _nfcState.value.copy(isEnabled = value)
    }

    fun toggleWritingMode() {
        _nfcState.value = _nfcState.value.copy(isWriting = !_nfcState.value.isWriting)
    }

    fun updateMessageToWrite(value: String) {
        _nfcState.value = _nfcState.value.copy(messageToWrite = value)
    }

    fun readTag(tag: android.nfc.Tag?, messages: Array<android.nfc.NdefMessage>?) {
        viewModelScope.launch {
            val tagData = repository.readTag(tag, messages)
            tagData?.let {
                _nfcState.value = _nfcState.value.copy(
                    lastTag = it,
                    techList = it.techList ?: "",
                    records = it.payload
                )
            }
        }
    }

    fun writeTag(tag: android.nfc.Tag?) {
        viewModelScope.launch {
            val success = repository.writeTag(tag, _nfcState.value.messageToWrite)
            _nfcState.value = _nfcState.value.copy(
                records = if (success) "Write successful" else "Write failed"
            )
        }
    }

    fun reset() {
        _nfcState.value = NfcUiState()
    }
}

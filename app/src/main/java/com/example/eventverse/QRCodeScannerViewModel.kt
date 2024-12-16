package com.example.eventverse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class QRCodeScannerViewModel : ViewModel() {
    private val _barcodeResult = MutableLiveData<String>()
    val barcodeResult: LiveData<String> get() = _barcodeResult

    fun updateBarcodeResult(result: String) {
        _barcodeResult.value = result
    }
}

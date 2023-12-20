package com.myapplication.medease.ui.screens.camera

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.remote.response.ObatData
import com.myapplication.medease.data.repository.MedicineRepository
import com.myapplication.medease.ui.common.UiState
import com.myapplication.medease.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.internal.wait

class CameraViewModel(
    private val medicineRepository: MedicineRepository
) : ViewModel() {
    private val _uploadImageState : MutableStateFlow<UiState<Event<ObatData>>> = MutableStateFlow(UiState.Loading)
    val uploadImageState : StateFlow<UiState<Event<ObatData>>> = _uploadImageState

    private val _isUpload = mutableStateOf(false)
    val isUpload: State<Boolean> = _isUpload

    fun uploadImage(context: Context, image: MultipartBody.Part, navigateBack: () -> Unit) {
        _isUpload.value = true
        viewModelScope.launch {
            medicineRepository.uploadImage(image)
                .catch {
                    _uploadImageState.value = UiState.Error(it.message.toString())
                    _isUpload.value = false
                    Toast.makeText(context, "medicine not found, try with another angle", Toast.LENGTH_SHORT).show()
                    navigateBack.invoke()
                }
                .collect{ medicine ->
                    _uploadImageState.value = UiState.Success(Event(medicine))
                }
        }
    }
}
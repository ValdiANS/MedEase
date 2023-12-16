package com.myapplication.medease.ui.screens.detail_medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.remote.response.DataItem
import com.myapplication.medease.data.repository.MedicineRepository
import com.myapplication.medease.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(
private val medicineRepository: MedicineRepository
) : ViewModel() {
    private val _medicineState : MutableStateFlow<UiState<DataItem>> = MutableStateFlow(UiState.Loading)
    val medicineState: StateFlow<UiState<DataItem>> get() = _medicineState

    fun getDetailMedicineById(id: String) {
        viewModelScope.launch {
            medicineRepository.getAllMedicine().collect{ medicine ->
                val result = medicine.find { it.id == id }
                if (result != null) {
                    _medicineState.value = UiState.Success(result)
                } else {
                    _medicineState.value = UiState.Error("")
                }
            }
        }
    }
}
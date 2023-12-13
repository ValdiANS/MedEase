package com.myapplication.medease.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.remote.response.DataItem
import com.myapplication.medease.data.repository.MedicineRepository
import com.myapplication.medease.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeViewModel(
    private val medicineRepository: MedicineRepository
) : ViewModel() {
    private val _listMedicineState : MutableStateFlow<UiState<List<DataItem>>> = MutableStateFlow(UiState.Loading)
    val listMedicineState : StateFlow<UiState<List<DataItem>>> get() = _listMedicineState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _searchedMedicine : MutableStateFlow<UiState<List<DataItem>>> = MutableStateFlow(UiState.Loading)
    val searchedMedicine : StateFlow<UiState<List<DataItem>>> get() = _searchedMedicine

    private val _recentSearched : MutableStateFlow<UiState<List<DataItem>>> = MutableStateFlow(UiState.Error(""))
    val recentSearched : StateFlow<UiState<List<DataItem>>> get() = _recentSearched

    init {
        getAllMedicine()
    }

    private fun getAllMedicine() {
        _listMedicineState.value = UiState.Loading
        viewModelScope.launch {
            medicineRepository.getAllMedicine()
                .catch {
                    _listMedicineState.value = UiState.Error(it.message.toString())
                }
                .collect{ listMedicine ->
                    _listMedicineState.value = UiState.Success(listMedicine)
                }
            Log.d("homeviewmodel", "${_listMedicineState.value}")
        }
    }

    fun searchMedicine(newQuery: String) {
        _searchedMedicine.value = UiState.Loading
        _query.value = newQuery
        viewModelScope.launch {
            medicineRepository.getAllMedicine()
                .catch {
                    _searchedMedicine.value = UiState.Error(it.message.toString())
                }
                .collect{ listMedicine ->
                    val searched =  listMedicine.filter {
                        it.nama.contains(_query.value, ignoreCase = true)
                    }
                    if (searched.isEmpty()) {
                        _searchedMedicine.value = UiState.Error(searched.size.toString())
                    } else {
                        _searchedMedicine.value = UiState.Success(searched)
                        _recentSearched.value = _searchedMedicine.value
                    }
                }
        }
    }
}
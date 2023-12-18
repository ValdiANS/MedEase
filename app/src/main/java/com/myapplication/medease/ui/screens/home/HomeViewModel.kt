package com.myapplication.medease.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.local.entity.MedicineEntity
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
    private val _listMedicineState : MutableStateFlow<UiState<List<MedicineEntity>>> = MutableStateFlow(UiState.Loading)
    val listMedicineState : StateFlow<UiState<List<MedicineEntity>>> get() = _listMedicineState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _searchedMedicine : MutableStateFlow<UiState<List<MedicineEntity>>> = MutableStateFlow(UiState.Loading)
    val searchedMedicine : StateFlow<UiState<List<MedicineEntity>>> get() = _searchedMedicine

    private val _recentSearched : MutableStateFlow<UiState<List<MedicineEntity>>> = MutableStateFlow(UiState.Error(""))
    val recentSearched : StateFlow<UiState<List<MedicineEntity>>> get() = _recentSearched

    init {
        getAllMedicine()
        getAllRecentMedicine()
    }

    private fun getAllMedicine() {
        _listMedicineState.value = UiState.Loading
        viewModelScope.launch {
            medicineRepository.getAllMedicine()
                .catch {
                    _listMedicineState.value = UiState.Error(it.message.toString())
                }
                .collect{ medicineResponse ->
                    if (medicineResponse.isEmpty()) {
                        _listMedicineState.value = UiState.Error(medicineResponse.size.toString())
                    } else {
                        val listMedicine = medicineResponse.map {
                            MedicineEntity(
                                id = it.id,
                                name = it.nama,
                                type = it.tipe.nama,
                                doses = it.kapasitas,
                                description = it.deskripsi
                            )
                        }
                        _listMedicineState.value = UiState.Success(listMedicine)
                    }
                }
        }
    }

    fun getAllRecentMedicine() {
        _recentSearched.value = UiState.Loading
        viewModelScope.launch {
            medicineRepository.getAllRecentMedicine()
                .catch {
                    _recentSearched.value = UiState.Error(it.message.toString())
                }
                .collect{ listMedicine ->
                    if (listMedicine.isEmpty()) {
                        _recentSearched.value = UiState.Error(listMedicine.size.toString())
                    } else {
                        _recentSearched.value = UiState.Success(listMedicine)
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
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
                        val listSearched = searched.map {
                            MedicineEntity(
                                it.id,
                                it.nama,
                                it.tipe.nama,
                                it.kapasitas,
                                it.deskripsi
                            )
                        }
                        _searchedMedicine.value = UiState.Success(listSearched)
                            listSearched.forEach {
                            medicineRepository.insertRecentMedicine(it)
                        }
                    }
                }
        }
    }
}
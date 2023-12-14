package com.myapplication.medease.ui.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleWithTime
import com.myapplication.medease.data.repository.ScheduleRepository
import com.myapplication.medease.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ScheduleScreenViewModel(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {
    private val _listScheduleState: MutableStateFlow<UiState<List<ScheduleWithTime>>> =
        MutableStateFlow(
            UiState.Loading
        )
    val listScheduleState: StateFlow<UiState<List<ScheduleWithTime>>> get() = _listScheduleState

    fun getAllSchedule() {
        _listScheduleState.value = UiState.Loading

        viewModelScope.launch {

            scheduleRepository.getAllScheduleAndTime().asFlow()
                .catch {
                    _listScheduleState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _listScheduleState.value = UiState.Success(it)
                }
        }
    }

    fun deleteSchedule(schedule: ScheduleEntity) {
        scheduleRepository.deleteSchedule(schedule)
    }
}
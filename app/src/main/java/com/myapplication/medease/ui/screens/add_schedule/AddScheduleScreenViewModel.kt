package com.myapplication.medease.ui.screens.add_schedule

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.repository.ScheduleRepository
import com.myapplication.medease.notification.ScheduleReminderReceiver
import kotlinx.coroutines.launch
import java.util.Calendar

class AddScheduleScreenViewModel(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {

    private val _enteredMedicineName = mutableStateOf("")
    var enteredMedicineName: State<String> = _enteredMedicineName

    private val _enteredTime = mutableStateOf("")
    var enteredTime: State<String> = _enteredTime

    private val _enteredTimeList = mutableStateOf(mutableListOf<String>())
    var enteredTimeList: State<List<String>> = _enteredTimeList

    fun enteredMedicineNameChangeHandler(newName: String) {
        _enteredMedicineName.value = newName
    }

    fun setEnteredTime(time: String) {
        _enteredTime.value = time
    }

    fun addEnteredTimeToList(time: String) {
        _enteredTimeList.value.add(time)
        _enteredTime.value = ""
    }

    fun getSession(): LiveData<UserModel> {
        return scheduleRepository.getSession().asLiveData()
    }

    fun addScheduleToDatabase(context: Context, userId: String) {
        val scheduleReminder = ScheduleReminderReceiver()
        val currentTime = Calendar.getInstance().timeInMillis.toInt()

        viewModelScope.launch {
            scheduleRepository.insertSchedule(
                ScheduleEntity(
                    scheduleId = currentTime,
                    userId = userId,
                    medicineName = enteredMedicineName.value,
                    dose = 1,
                    frequency = enteredTimeList.value.size,
                )
            )

            enteredTimeList.value.forEach {
                scheduleRepository.insertScheduleTime(
                    ScheduleTimeEntity(
                        scheduleId = currentTime,
                        time = it,
                    )
                )
            }

            val scheduleWithTime = scheduleRepository.getScheduleAndTimesById(currentTime)

            scheduleReminder.setScheduleReminder(
                context = context,
                schedule = scheduleWithTime.schedule,
                listScheduleTime = scheduleWithTime.times
            )
        }
    }
}
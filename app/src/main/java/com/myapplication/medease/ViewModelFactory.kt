package com.myapplication.medease

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.data.repository.MedicineRepository
import com.myapplication.medease.data.repository.ScheduleRepository
import com.myapplication.medease.ui.screens.add_schedule.AddScheduleScreenViewModel
import com.myapplication.medease.ui.screens.authentication.LoginFormViewModel
import com.myapplication.medease.ui.screens.authentication.RegisterFormViewModel
import com.myapplication.medease.ui.screens.home.HomeViewModel
import com.myapplication.medease.ui.screens.schedule.ScheduleScreenViewModel
import com.myapplication.medease.utils.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val authenticationRepository: AuthenticationRepository,
    private val medicineRepository: MedicineRepository,
    private val scheduleRepository: ScheduleRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginFormViewModel::class.java) ->
                LoginFormViewModel(authenticationRepository) as T

            modelClass.isAssignableFrom(RegisterFormViewModel::class.java) ->
                RegisterFormViewModel(authenticationRepository) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(authenticationRepository) as T

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(medicineRepository) as T

            modelClass.isAssignableFrom(ScheduleScreenViewModel::class.java) ->
                ScheduleScreenViewModel(scheduleRepository) as T

            modelClass.isAssignableFrom(AddScheduleScreenViewModel::class.java) ->
                AddScheduleScreenViewModel(scheduleRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    authenticationRepository = Injection.provideAuthenticationRepository(context),
                    medicineRepository = Injection.provideMedicineRepository(context),
                    scheduleRepository = Injection.provideScheduleRepository(context)
                )
            }.also { INSTANCE = it }
    }
}
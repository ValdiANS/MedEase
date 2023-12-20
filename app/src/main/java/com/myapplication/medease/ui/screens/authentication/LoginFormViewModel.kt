package com.myapplication.medease.ui.screens.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.myapplication.medease.data.remote.response.LoginResponse
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.utils.isEmail
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginFormViewModel(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _emailValue = mutableStateOf("")
    var emailValue: State<String> = _emailValue

    private val _passwordValue = mutableStateOf("")
    var passwordValue: State<String> = _passwordValue

    private val _isEmailError = mutableStateOf(false)
    var isEmailError: State<Boolean> = _isEmailError

    private val _isPasswordError = mutableStateOf(false)
    var isPasswordError: State<Boolean> = _isPasswordError

    private val _isPasswordVisible = mutableStateOf(false)
    var isPasswordVisible: State<Boolean> = _isPasswordVisible

    private val _isSubmitting = mutableStateOf(false)
    var isSubmitting: State<Boolean> = _isSubmitting

    private val _isFormValid = mutableStateOf(true)
    var isFormValid: State<Boolean> = _isFormValid

    private val _submitErrorMsg = mutableStateOf("")
    var submitErrorMsg: State<String> = _submitErrorMsg

    val submitEnabled = derivedStateOf {
        !isEmailError.value &&
                !isPasswordError.value &&
                emailValue.value.isNotEmpty() &&
                passwordValue.value.isNotEmpty()
    }

    fun onEmailChangeHandler(newInput: String) {
        _emailValue.value = newInput.trim()

        _isEmailError.value = when {
            newInput.isEmpty() -> false
            !newInput.isEmail() -> true
            else -> false
        }

        if (!isFormValid.value) {
            _isFormValid.value = true
        }
    }

    fun onPasswordChangeHandler(newInput: String) {
        _passwordValue.value = newInput.trim()

        _isPasswordError.value = when {
            newInput.isEmpty() -> false
            newInput.length < 8 -> true
            else -> false
        }

        if (!isFormValid.value) {
            _isFormValid.value = true
        }
    }

    fun onPasswordTrailingIconClickHandler() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun onSubmitHandler(onSuccessSignIn: () -> Unit) {
        viewModelScope.launch {
            _isSubmitting.value = true
            _submitErrorMsg.value = ""
            _isFormValid.value = true

            try {
                val loginResponse = login(emailValue.value, passwordValue.value)

                if (loginResponse.code == 200) {
                    onSuccessSignIn()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)

                when (errorResponse.code) {
                    401 -> {
                        _isFormValid.value = false
                        _submitErrorMsg.value = errorResponse.message
                    }

                    404 -> {
                        _isFormValid.value = false
                        _submitErrorMsg.value = errorResponse.message
                    }
                }
            }

            _isSubmitting.value = false
        }
    }

    private suspend fun login(email: String, password: String): LoginResponse {
        return authenticationRepository.login(email, password)
    }
}
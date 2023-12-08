package com.myapplication.medease.ui.screens.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.utils.isEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun onSubmitHandler() {
        /*
        * TODO: email and password validation, then send data to the server
        * */

        viewModelScope.launch {
            _isSubmitting.value = true
            _submitErrorMsg.value = ""
            _isFormValid.value = true

            /*
             * TODO: This is test, delete later
             * */
            delay(500)

            val loginResponse = login(emailValue.value, passwordValue.value)

            _isFormValid.value = !loginResponse

            /*
            * TODO: This is test, delete later
            * */
            if (!isFormValid.value) {
                _submitErrorMsg.value = "Invalid Account!"
            }


            /**/

            _isSubmitting.value = false
        }
    }

    private suspend fun login(email: String, password: String): Boolean {
        val response = authenticationRepository.login(email, password)

        return response
    }
}
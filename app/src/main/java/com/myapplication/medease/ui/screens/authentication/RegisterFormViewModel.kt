package com.myapplication.medease.ui.screens.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.myapplication.medease.data.remote.response.LoginResponse
import com.myapplication.medease.data.remote.response.RegisterResponse
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.utils.isEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterFormViewModel(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    // Full Name
    private val _fullNameValue = mutableStateOf("")
    var fullNameValue: State<String> = _fullNameValue

    private val _isFullNameError = mutableStateOf(false)
    var isFullNameError: State<Boolean> = _isFullNameError

    // Username
    private val _usernameValue = mutableStateOf("")
    var usernameValue: State<String> = _usernameValue

    private val _isUsernameError = mutableStateOf(false)
    var isUsernameError: State<Boolean> = _isUsernameError

    // Email
    private val _emailValue = mutableStateOf("")
    var emailValue: State<String> = _emailValue

    private val _isEmailError = mutableStateOf(false)
    var isEmailError: State<Boolean> = _isEmailError

    // Password
    private val _passwordValue = mutableStateOf("")
    var passwordValue: State<String> = _passwordValue

    private val _isPasswordError = mutableStateOf(false)
    var isPasswordError: State<Boolean> = _isPasswordError

    private val _isPasswordVisible = mutableStateOf(false)
    var isPasswordVisible: State<Boolean> = _isPasswordVisible

    // Birthdate
    private val _birthdateValue = mutableStateOf("")
    var birthdateValue: State<String> = _birthdateValue

    private val _isBirthdateError = mutableStateOf(false)
    var isBirthdateError: State<Boolean> = _isBirthdateError

    private val _isOpenDatePickerDialog = mutableStateOf(false)
    var isOpenDatePickerDialog: State<Boolean> = _isOpenDatePickerDialog


    // Form
    private val _isSubmitting = mutableStateOf(false)
    var isSubmitting: State<Boolean> = _isSubmitting

    private val _isFormValid = mutableStateOf(true)
    var isFormValid: State<Boolean> = _isFormValid

    private val _submitErrorMsg = mutableStateOf("")
    var submitErrorMsg: State<String> = _submitErrorMsg

    val submitEnabled = derivedStateOf {
        !isFullNameError.value &&
                !isUsernameError.value &&
                !isEmailError.value &&
                !isPasswordError.value &&
                !isBirthdateError.value &&
                fullNameValue.value.isNotEmpty() &&
                usernameValue.value.isNotEmpty() &&
                emailValue.value.isNotEmpty() &&
                passwordValue.value.isNotEmpty() &&
                birthdateValue.value.isNotEmpty()
    }

    fun onFullNameChangeHandler(newInput: String) {
        _fullNameValue.value = newInput.trimStart()

        _isFullNameError.value = when {
            newInput.isEmpty() -> false
            else -> false
        }

        if (!isFormValid.value) {
            _isFormValid.value = true
        }
    }

    fun onUsernameChangeHandler(newInput: String) {
        _usernameValue.value = newInput.trim()

        _isUsernameError.value = when {
            newInput.isEmpty() -> false
            else -> false
        }

        if (!isFormValid.value) {
            _isFormValid.value = true
        }
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

    fun onBirthdateChangeHandler(newInput: String) {
        _birthdateValue.value = newInput.trim()

        _isBirthdateError.value = when {
            newInput.isEmpty() -> false
            else -> false
        }

        if (!isFormValid.value) {
            _isFormValid.value = true
        }
    }

    fun openDatePickerDialog() {
        _isOpenDatePickerDialog.value = true
    }

    fun dismissDatePickerDialog() {
        _isOpenDatePickerDialog.value = false
    }

    fun onSubmitHandler(onSuccessSignUp: () -> Unit) {
        viewModelScope.launch {
            _isSubmitting.value = true
            _submitErrorMsg.value = ""
            _isFormValid.value = true

            try {
                val registerResponse = register(
                    fullName = fullNameValue.value,
                    username = usernameValue.value,
                    email = emailValue.value,
                    password = passwordValue.value,
                    birthdate = birthdateValue.value,
                )

                when (registerResponse.code) {
                    201 -> {
                        // Reset form
                        _fullNameValue.value = ""
                        _usernameValue.value = ""
                        _emailValue.value = ""
                        _passwordValue.value = ""
                        _birthdateValue.value = ""

                        onSuccessSignUp()
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)

                when (errorResponse.code) {
                    500 -> {
                        _isFormValid.value = false
                        _submitErrorMsg.value = "Username or Email is used!"
                    }
                }
            }

            _isSubmitting.value = false
        }
    }

    private suspend fun register(
        fullName: String,
        username: String,
        email: String,
        password: String,
        birthdate: String,
    ): RegisterResponse {
        return authenticationRepository.register(
            fullName = fullName,
            username = username,
            email = email,
            password = password,
            birthdate = birthdate,
        )
    }
}
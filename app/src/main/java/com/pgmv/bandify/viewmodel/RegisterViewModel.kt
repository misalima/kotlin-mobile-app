package com.pgmv.bandify.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    var firstName by mutableStateOf("")
        private set

    var surname by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var firstNameHasErrors by mutableStateOf(false)
    var surnameHasErrors by mutableStateOf(false)
    var emailHasErrors by mutableStateOf(false)
    var passwordHasErrors by mutableStateOf(false)
    var confirmPasswordHasErrors by mutableStateOf(false)

    fun updatefirstName(input: String) {
        firstName = input
        firstNameHasErrors = firstName.isEmpty()
    }

    fun updateSurname(input: String) {
        surname = input
        surnameHasErrors = surname.isEmpty()
    }

    fun updateEmail(input: String) {
        email = input
        emailHasErrors = email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updatePassword(input: String) {
        password = input
        passwordHasErrors = password.isEmpty() || password.length < 6
    }

    fun updateConfirmPassword(input: String) {
        confirmPassword = input
        confirmPasswordHasErrors = confirmPassword.isEmpty() || confirmPassword != password
    }
}
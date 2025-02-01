package com.pgmv.bandify.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pgmv.bandify.database.DatabaseHelper
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var loginError by mutableStateOf<String?>(null)
    var loggedInUserId by mutableStateOf<Long?>(null)
    var wasLoginAttempted by mutableStateOf(false)

    fun updateEmail(input: String) {
        email = input
        if (wasLoginAttempted) {
            validateFields()
        }
    }

    fun updatePassword(input: String) {
        password = input
        if (wasLoginAttempted) {
            validateFields()
        }
    }

    fun validateFields(): Boolean {
        emailError = when {
            email.isEmpty() -> "Campo obrigatório"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de e-mail inválido"
            else -> null
        }

        passwordError = when {
            password.isEmpty() -> "Campo obrigatório"
            password.length < 6 -> "A senha deve ter pelo menos 6 caracteres"
            else -> null
        }

        return emailError == null && passwordError == null
    }

    fun login(onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        wasLoginAttempted = true
        if (!validateFields()) {
            loginError = "Por favor, corrija os campos destacados."
            return
        }

        viewModelScope.launch {
            try {
                val user = dbHelper.userDao().validateUser(email, password)
                if (user != null) {
                    loggedInUserId = user.id
                    onSuccess(user.id)
                } else {
                    loginError = "Usuário ou senha inválidos."
                    onError("Usuário ou senha inválidos.")
                }
            } catch (e: Exception) {
                loginError = "Erro ao conectar ao banco de dados."
                onError("Erro ao conectar ao banco de dados.")
            }
        }
    }
}

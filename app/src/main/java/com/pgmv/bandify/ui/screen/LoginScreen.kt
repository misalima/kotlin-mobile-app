package com.pgmv.bandify.ui.screen


import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.pgmv.bandify.R
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


class UserViewModel : ViewModel() {
    var userId by mutableStateOf<Long?>(null)
}

@Composable
fun LoginScreen(
    authenticationViewModel: AuthenticationViewModel,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val email = authenticationViewModel.email
    val password = authenticationViewModel.password
    val emailError = authenticationViewModel.emailError
    val passwordError = authenticationViewModel.passwordError
    var visiblePassword by remember { mutableStateOf(false) }
    val loginError = authenticationViewModel.loginError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_reverse_transparent),
            contentDescription = "Logo da aplicação",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 32.dp)
        )

        Text(
            text = "Faça login na sua conta\n para continuar",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { authenticationViewModel.updateEmail(it) },
            label = { Text("Email") },
            isError = emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            supportingText = {
                emailError?.let { errorMsg ->
                    Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { authenticationViewModel.updatePassword(it) },
            label = { Text("Senha") },
            isError = passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visiblePassword = !visiblePassword }) {
                    Icon(
                        imageVector = if (visiblePassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (visiblePassword) "Ocultar senha" else "Mostrar senha"
                    )
                }
            },
            supportingText = {
                passwordError?.let { errorMsg ->
                    Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true
        )

        if (loginError != null) {
            Text(
                text = loginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                authenticationViewModel.login(
                    onSuccess = {
                        userViewModel.userId = authenticationViewModel.loggedInUserId
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onError = {}
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ainda não possui uma conta?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        TextButton(
            onClick = { navController.navigate("register") },
        ) {
            Text(
                text = "Cadastre-se",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

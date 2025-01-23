package com.pgmv.bandify.ui.screen

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.pgmv.bandify.R
import com.pgmv.bandify.database.DatabaseHelper


class AuthenticationViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var emailHasErrors by mutableStateOf(false)
    var passwordHasErrors by mutableStateOf(false)

    fun updateEmail(input: String) {
        email = input
        emailHasErrors = email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updatePassword(input: String) {
        password = input
        passwordHasErrors = password.isEmpty() || password.length < 6
    }
}

@Composable
fun LoginScreen(
    authenticationViewModel: AuthenticationViewModel
) {
    val email = authenticationViewModel.email
    val password = authenticationViewModel.password
    val emailHasErrors = authenticationViewModel.emailHasErrors
    val passwordHasErrors = authenticationViewModel.passwordHasErrors
    var visiblePassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_reverse_transparent),
            contentDescription = "Logo da aplicação",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(
            text = "Faça login na sua conta\npara continuar",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "EMAIL",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = email,
                onValueChange = { authenticationViewModel.updateEmail(it) },
                isError = emailHasErrors,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                supportingText = {
                    if (emailHasErrors) {
                        Text("Formato de e-mail incorreto.",
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )

            Text(
                text = "SENHA",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = password,
                onValueChange = { authenticationViewModel.updatePassword(it) },
                isError = passwordHasErrors,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                supportingText = {
                    if (passwordHasErrors) {
                        Text("A senha deve ter pelo menos 6 caracteres.",
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { visiblePassword = !visiblePassword }) {
                        Icon(
                            imageVector = if (visiblePassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (visiblePassword) "Ocultar senha" else "Mostrar senha",
                        )
                    }
                }
            )
        }

        Button(

                onClick = {

                }
            ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 15.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Entrar",
                fontSize = 20.sp,
            )
        }

        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = "Ainda não possui uma conta?",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Cadastre-se",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}


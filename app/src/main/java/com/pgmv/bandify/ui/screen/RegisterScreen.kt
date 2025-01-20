package com.pgmv.bandify.ui.screen


import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pgmv.bandify.R
import com.pgmv.bandify.ui.theme.BandifyTheme

class RegisterViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var nameHasErrors by mutableStateOf(false)
    var emailHasErrors by mutableStateOf(false)
    var passwordHasErrors by mutableStateOf(false)
    var confirmPasswordHasErrors by mutableStateOf(false)

    fun updateName(input: String) {
        name = input
        nameHasErrors = name.isEmpty()
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
@Composable
fun RegisterScreen() {
    val viewModel: RegisterViewModel = viewModel()
    val nameRegister by remember { mutableStateOf(viewModel.name) }
    val emailRegister by remember { mutableStateOf(viewModel.email) }
    val passwordRegister by remember { mutableStateOf(viewModel.password) }
    val confirmPasswordRegister by remember { mutableStateOf(viewModel.confirmPassword) }

    val nameError = if (viewModel.nameHasErrors) "Nome não pode ser vazio" else ""
    val emailError = if (viewModel.emailHasErrors) "Formato de email inválido" else ""
    val passwordError = if (viewModel.passwordHasErrors) "Senha deve ter no mínimo 6 caracteres" else ""
    val confirmPasswordError = if (viewModel.confirmPasswordHasErrors) "As senhas não coincidem" else ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_reverse_transparent),
            contentDescription = "Logo da aplicação",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Text(
            text = "Cadastra-se",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Informe o seus dados para\npara criarmos sua conta\nBandify",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "SEU PRIMEIRO NOME",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = nameRegister,
                onValueChange = { viewModel.updateName(it) },

                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )
            if (nameError.isNotEmpty()) {
                Text(
                    text = nameError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "EMAIL",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = emailRegister,
                onValueChange = { viewModel.updateEmail(it) },

                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )
            if (emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "CRIE A SUA SENHA",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = passwordRegister,
                onValueChange = { viewModel.updatePassword(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (viewModel.passwordHasErrors) VisualTransformation.None else PasswordVisualTransformation(),
            )
            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "CONFIRME A SUA SENHA",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = confirmPasswordRegister,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (viewModel.confirmPasswordHasErrors) VisualTransformation.None else PasswordVisualTransformation(),
            )
            if (confirmPasswordError.isNotEmpty()) {
                Text(
                    text = confirmPasswordError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }

        Button(
            onClick = {
                viewModel.updateName(nameRegister)
                viewModel.updateEmail(emailRegister)
                viewModel.updatePassword(passwordRegister)
                viewModel.updateConfirmPassword(confirmPasswordRegister)

                if (!viewModel.nameHasErrors && !viewModel.emailHasErrors && !viewModel.passwordHasErrors && !viewModel.confirmPasswordHasErrors) {

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Cadastrar",
                fontSize = 20.sp,
            )
        }
    }
}

package com.pgmv.bandify.ui.screen

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pgmv.bandify.R
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.User
import com.pgmv.bandify.viewmodel.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@Composable
fun RegisterScreen(navController: NavHostController) {
    val viewModel: RegisterViewModel = viewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
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
            text = "Informe os seus dados para\ncriarmos sua conta Bandify",
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
                value = viewModel.firstName,
                onValueChange = { viewModel.updatefirstName(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )
            if (viewModel.firstNameHasErrors) {
                Text(
                    text = "Nome não pode ser vazio",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "SEU SOBRENOME",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = viewModel.surname,
                onValueChange = { viewModel.updateSurname(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )
            if (viewModel.surnameHasErrors) {
                Text(
                    text = "Nome não pode ser vazio",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "EMAIL",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
            )
            if (viewModel.emailHasErrors) {
                Text(
                    text = "Formato de email inválido",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "CRIE A SUA SENHA",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Mostrar/Ocultar Senha"
                        )
                    }
                }
            )
            if (viewModel.passwordHasErrors) {
                Text(
                    text = "Senha deve ter no mínimo 6 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "CONFIRME A SUA SENHA",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Mostrar/Ocultar Senha"
                        )
                    }
                }
            )
            if (viewModel.confirmPasswordHasErrors) {
                Text(
                    text = "As senhas não coincidem",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 15.dp),
            text = buildAnnotatedString {
                append("Ao cadastrar, você concorda com os ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Termos e\n Condições de Uso e Privacidade")
                }
                append(" do Bandify")
            },
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                viewModel.updatefirstName(viewModel.firstName)
                viewModel.updateSurname(viewModel.surname)
                viewModel.updateEmail(viewModel.email)
                viewModel.updatePassword(viewModel.password)
                viewModel.updateConfirmPassword(viewModel.confirmPassword)

                if (!viewModel.firstNameHasErrors &&
                    !viewModel.surnameHasErrors &&
                    !viewModel.emailHasErrors &&
                    !viewModel.passwordHasErrors &&
                    !viewModel.confirmPasswordHasErrors
                ) {
                    val dbHelper = DatabaseHelper.getInstance(context)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val newUser = User(
                                firstName = viewModel.firstName,
                                email = viewModel.email,
                                password = viewModel.password,
                                surname = viewModel.surname,
                                username = (viewModel.firstName + viewModel.surname).lowercase(),
                                phone = "",
                            )
                            val rowId = dbHelper.userDao().insertUser(newUser)
                            withContext(Dispatchers.Main) {
                                if (rowId != -1L) {
                                    Log.d("Register", "User inserted with ID: $rowId")
                                    Toast.makeText(
                                        context,
                                        "Usuário cadastrado com sucesso!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Log.d("Register", "Error inserting user")
                                    Toast.makeText(
                                        context,
                                        "Erro ao cadastrar usuário!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.e("Register", "Database error: ${e.message}", e)
                                Toast.makeText(
                                    context,
                                    "Erro no banco de dados: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 10.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Cadastrar",
                fontSize = 20.sp,
            )
        }

        Text(
            text = "Já tem uma conta?",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        TextButton(
            onClick = {
                navController.navigate("login")
            },
        ) {
            Text(
                text = "Faça login!",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

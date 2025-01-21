package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import com.pgmv.bandify.database.DatabaseHelper



@Composable
fun PerfilScreen(databaseHelper: DatabaseHelper, userId: Int) {
    val userDao = databaseHelper.userDao()
    val userFlow = userDao.getUserById(userId).collectAsState(initial = null)
    val user = userFlow.value

    if (user == null) {
        Text(
            text = "Carregando ou usuário não encontrado.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(vertical = 30.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = user.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Vocalista Principal",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary

                    )

                }

            }


            Row(
                modifier = Modifier
                    .padding(start = 35.dp, top = 30.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Phone Icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                     text = user.phone,
                     fontSize = 15.sp,
                     color = MaterialTheme.colorScheme.onSecondary

                )


            }

            Row(
                modifier = Modifier
                    .padding(start = 35.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                     text = user.email,
                     fontSize = 15.sp,
                     color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 60.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(
                    onClick = {  },

                ) {
                    Text(
                        text = "Editar Informações",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary

                    )
                }

            }

            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LockReset,
                    contentDescription = "LockReset Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(
                    onClick = {  },

                    ) {
                    Text(
                        text = "Alterar Senha",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary

                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(
                    onClick = {  },

                    ) {
                    Text(
                        text = "Configurações",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary

                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 220.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = {  },

                    ) {
                    Text(
                        text = "Sair",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.error

                    )
                }
            }
        }
    }
}


package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Square
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.pgmv.bandify.R
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.ui.components.AgendaEventCard
import com.pgmv.bandify.ui.components.Day
import com.pgmv.bandify.ui.components.DaysOfWeekTitle
import com.pgmv.bandify.ui.components.MonthTitle
import com.pgmv.bandify.ui.theme.BandifyTheme
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun PerfilScreen(dbHelper: DatabaseHelper? = null) {
    val userDao = dbHelper?.userDao()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 30.dp),
    ) {
        Row(
            modifier = Modifier
                .padding( horizontal = 30.dp)
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
            Column (){
                Text(
                text = "Francisco Covas",
                    fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary

            )
                Text(
                    text = "Vocalista Principal",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )  }

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
                text = "(82) 94422-6533",
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
                text = "francovas1@gmail.com",
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
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Editar Informações",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
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
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Alterar Senha",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
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
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Configurações",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
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
            Text(
                text = "Sair",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
@Preview
@Composable
fun PerfilScreenPreview() {
    BandifyTheme {
        PerfilScreen()  }

}



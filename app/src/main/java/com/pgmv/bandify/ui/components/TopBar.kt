package com.pgmv.bandify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgmv.bandify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    screenTitle: String,
    isHomeScreen: Boolean) {

    val bandName = "Banda Som e Louvor"

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically) {

                if (isHomeScreen){
                    Image(
                        painter = painterResource(id = R.drawable.logo_bandify),
                        contentDescription = "Band Logo",
                        modifier = Modifier
                            .size(26.dp)
                    )
                }

                Text(
                    text = if (isHomeScreen) "Bandify" else screenTitle,
                    fontSize = 22.sp,
                    color = Color(0xFFFFC436),
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, Color.White, CircleShape)
                        .background(Color.Gray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_banda),
                        contentDescription = "Band Logo",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(start = 6.dp))

                Text(
                    text = bandName,
                    fontSize = 14.sp,
                    color = Color.White,
                )

                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }
        },
        modifier = Modifier.height(64.dp),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF011535))
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar("Repertório", false)
}

package com.pgmv.bandify.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Calendário", "Repertório", "Arquivos", "Perfil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.DateRange,
        Icons.Default.LibraryMusic,
        Icons.Default.UploadFile,
        Icons.Default.AccountCircle
    )

    TabRow(
        modifier = Modifier.height(52.dp),
        selectedTabIndex = selectedIndex,
        containerColor = Color(0xFF011535),
        divider = {},
        indicator = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            val iconOffset by animateDpAsState(
                targetValue = if (selectedIndex == index) (-2).dp else 0.dp,
                label = "Icon Offset Animation")

            Tab(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            ) {
                Icon(
                    imageVector = icons[index],
                    contentDescription = tab,
                    tint = if (selectedIndex == index) Color.White else Color(0xFFFFC436),
                    modifier = Modifier
                        .size(24.dp)
                        .offset{ IntOffset(0, iconOffset.roundToPx()) }
                )

               Column(
                   modifier = Modifier
                       .clip(CircleShape)
                       .background(Color.White)
                       .height(if (selectedIndex == index) 6.dp else 0.dp)
                       .size(24.dp)
                       .width(24.dp)
               ){}
            }
        }
    }
}

@Composable
@Preview
fun BottomBarPreview() {
    BottomBar()
}

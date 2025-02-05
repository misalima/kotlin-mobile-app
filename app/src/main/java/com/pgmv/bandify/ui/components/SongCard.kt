package com.pgmv.bandify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun SongCard(song: Song, onDelete: (Song) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = { }
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.MusicNote,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Nota Musical"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Text(
                    text = song.artist,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Timer,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = song.duration,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Card (
                modifier = Modifier.padding(horizontal = 12.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEEEEEE)
                )
            ){
                Row (
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "Tom:",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Light,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "Tempo: ",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Light,
                            fontSize = 10.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = song.key,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = song.tempo.toString(),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(23.dp)
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Download,
                    modifier = Modifier.size(23.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Play"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                onClick = { onDelete(song) },
                modifier = Modifier
                    .size(22.dp)
            ){
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongCardPreview() {
    BandifyTheme {
        SongCard(
            song = Song(
                id = 1,
                title = "Medley Completo Aquieta Minha Alma",
                artist = "Anderson Freire",
                duration = "4:30",
                key = "C",
                tempo = 120,
                userId = 1,
                tag = "Ensaio"
            ),
            onDelete = { }
        )
    }
}
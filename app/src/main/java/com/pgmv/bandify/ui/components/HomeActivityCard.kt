package com.pgmv.bandify.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgmv.bandify.domain.ActivityHistory
import com.pgmv.bandify.ui.theme.BandifyTheme
import com.pgmv.bandify.ui.theme.Grey40
import com.pgmv.bandify.ui.theme.Grey60
import com.pgmv.bandify.utils.formatTimeDifference

@Composable
fun HomeActivityCard(
    activity: ActivityHistory
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = if(activity.activityType == "song") Icons.Default.MusicNote else if(activity.activityType == "file") Icons.Default.FileCopy else Icons.Default.Event,
                contentDescription = "Tipo de atividade",
                Modifier.size(32.dp).weight(0.5F),
            )
            Row (
                modifier = Modifier.weight(2.5F).padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = activity.activity,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight(700),
                        color = Grey60
                    )
                    Text(
                        text = activity.itemName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                }
            }
            Text(
                text = formatTimeDifference(activity.createdAt),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 8.sp,
                fontWeight = FontWeight(700),
                color = Grey40,
                modifier = Modifier.weight(1F).padding(4.dp)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(0.5F)
            ) {
                Icon(
                    imageVector = if (activity.activityType == "song" || activity.activityType == "file") Icons.Default.Download else Icons.Default.RemoveRedEye,
                    contentDescription = if (activity.activityType == "song" || activity.activityType == "file") "Baixar arquivo" else "Visualizar evento",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(32.dp)
                )
            }

        }
    }
}

    @Preview
    @Composable
    fun PreviewHomeActivityCard() {
        BandifyTheme {
            Scaffold { innerPading ->
                Surface (
                    modifier = Modifier.padding(innerPading)
                ) {
                    Column (
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HomeActivityCard(
                            activity = ActivityHistory(
                                userId = 1,
                                activity = "Uma nova música foi adicionada",
                                activityType = "song",
                                itemName = "Song Name",
                                id = 1,
                                createdAt = "2025-01-10T15:30:00",
                                songId = 24,
                            )
                        )
                        HomeActivityCard(
                            activity = ActivityHistory(
                                userId = 1,
                                activity = "Um novo evento foi adicionado",
                                activityType = "event",
                                itemName = "Very Big Event Name",
                                id = 2,
                                createdAt = "2025-01-09T15:30:00",
                                songId = 24,
                            )
                        )
                    }


                }
            }


        }
    }



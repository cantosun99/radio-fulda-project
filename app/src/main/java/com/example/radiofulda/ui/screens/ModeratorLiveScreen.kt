package com.example.radiofulda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.radiofulda.data.EventType
import com.example.radiofulda.services.EventBus

/**
 * Live-Feed für Moderatoren.
 *
 * Zeigt alle eingehenden Hörer-Rückmeldungen (Playlist-Bewertungen,
 * Songwünsche, Moderator-Bewertungen) in Echtzeit an, neueste zuerst.
 * Abonniert reaktiv den [EventBus] und aktualisiert sich automatisch.
 */
@Composable
fun ModeratorLiveScreen() {
    val events = EventBus.events.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (events.value.isEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Noch keine Events.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(events.value) { event ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(event.message, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = when (event.type) {
                                    EventType.PLAYLIST_RATING -> "Playlist-Bewertung"
                                    EventType.SONG_REQUEST -> "Songwunsch"
                                    EventType.MODERATOR_RATING -> "Moderator-Bewertung"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

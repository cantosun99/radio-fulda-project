package com.example.radiofulda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.radiofulda.Routes

/**
 * Einstiegsmenü des Moderator-Bereichs nach erfolgreicher Anmeldung.
 * Bietet Zugang zu Playlist-Auswahl und Live-Feed sowie eine Abmelden-Option.
 */
@Composable
fun ModeratorMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ModeratorMenuCard("Playlist auswählen", "Playlist für die Sendung festlegen") {
                navController.navigate(Routes.MODERATOR_PLAYLIST_SELECT)
            }
            ModeratorMenuCard("Live-Bewertungen", "Neue Bewertungen sofort sehen") {
                navController.navigate(Routes.MODERATOR_LIVE)
            }
        }

        Spacer(Modifier.weight(1f))

        ModeratorMenuCard("Abmelden", "Moderator-Bereich verlassen") {
            navController.popBackStack()
        }
    }
}

/** Navigierbare Karte für einen Moderator-Menüeintrag mit Titel und Untertitel. */
@Composable
private fun ModeratorMenuCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "›",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
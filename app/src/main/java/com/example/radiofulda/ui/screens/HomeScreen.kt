package com.example.radiofulda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.radiofulda.Routes

/**
 * Startbildschirm der App.
 *
 * Listet alle Funktionen für Hörer sowie den passwortgeschützten
 * Moderator-Zugang als navigierbare Karten auf.
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Radio Fulda",
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Ihr habt es euch gewünscht, wir spielen es: der Black Sabbath Marathon, nur heute und nur bei uns. \n Deine Musik, dein Radio Fulda.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "FÜR HÖRER",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NavCard("Aktueller Titel", "Interpret, Titel, Album") { navController.navigate(Routes.NOW_PLAYING) }
            NavCard("Playlist bewerten", "1-5 Sterne abgeben") { navController.navigate(Routes.PLAYLIST_RATING) }
            NavCard("Song wünschen", "Wunsch an die Redaktion") { navController.navigate(Routes.SONG_REQUEST) }
            NavCard("Moderator bewerten", "Feedback zur Moderation") { navController.navigate(Routes.MODERATOR_RATING) }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "FÜR MODERATOREN",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NavCard("Moderator-Login", "Zugang zum Moderator-Bereich") { navController.navigate(Routes.MODERATOR_LOGIN) }
        }

    }
}

/** Navigierbare Karte mit Titel, Untertitel und Chevron-Indikator. */
@Composable
private fun NavCard(title: String, subtitle: String, onClick: () -> Unit) {
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

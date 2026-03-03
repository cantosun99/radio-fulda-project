package com.example.radiofulda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.radiofulda.data.Album
import com.example.radiofulda.services.PlaylistSelectionService
import com.example.radiofulda.services.RadioCatalogService

/**
 * Zeigt den verfügbaren Sendekatalog und ermöglicht dem Moderator,
 * ein Album als aktive Sendungsplaylist festzulegen. Nach der Auswahl
 * wird [onDone] aufgerufen und der Bildschirm verlassen.
 */
@Composable
fun ModeratorPlaylistSelectScreen(
    catalogService: RadioCatalogService,
    selectionService: PlaylistSelectionService,
    onDone: () -> Unit
) {
    val albums = remember { catalogService.getAlbums() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(albums) { album ->
                AlbumRow(
                    album = album,
                    onClick = {
                        selectionService.selectAlbum(album)
                        onDone()
                    }
                )
            }
        }
    }
}

/** Zeigt Cover, Titelanzahl und Metadaten eines Albums als anklickbare Karte. */
@Composable
private fun AlbumRow(
    album: Album,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = album.coverResId),
                contentDescription = album.title,
                modifier = Modifier.size(64.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${album.tracks.size} Songs",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
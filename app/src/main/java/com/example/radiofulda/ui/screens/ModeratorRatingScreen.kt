package com.example.radiofulda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.radiofulda.services.ModeratorRatingService
import com.example.radiofulda.services.ModeratorService
import com.example.radiofulda.ui.viewmodel.ModeratorRatingViewModel

/**
 * Ermöglicht Hörern, einen Moderator aus der aktiven Liste auszuwählen,
 * mit 1–5 Sternen zu bewerten und einen optionalen Kommentar zu hinterlassen.
 */
@Composable
fun ModeratorRatingScreen(service: ModeratorRatingService, moderatorService: ModeratorService) {

    val viewModel: ModeratorRatingViewModel = viewModel(
        factory = viewModelFactory { initializer { ModeratorRatingViewModel(service, moderatorService) } }
    )
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            item {
                Text(
                    text = "AUSWAHL",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                )
            }

            itemsIndexed(state.moderators) { idx, name ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectModerator(idx) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(name, style = MaterialTheme.typography.titleMedium)
                            if (idx == state.selectedIndex) {
                                Text("Ausgewählt", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Text(
                            text = "›",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("BEWERTUNG", style = MaterialTheme.typography.labelLarge)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (i in 1..5) {
                                Text(
                                    text = if (i <= state.selectedRating) "★" else "☆",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.clickable { viewModel.setRating(i) }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.comment,
                        onValueChange = viewModel::updateComment,
                        placeholder = { Text("Kommentar (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        minLines = 2
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { if (!state.isSubmitting) viewModel.submit() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (state.isSubmitting) "Wird gesendet…" else "Bewertung absenden",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "›",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            state.statusMessage?.let { msg ->
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(msg, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            state.errorMessage?.let { msg ->
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(msg, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
}

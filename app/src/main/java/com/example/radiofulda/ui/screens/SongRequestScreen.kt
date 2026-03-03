package com.example.radiofulda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.radiofulda.services.SongRequestService
import com.example.radiofulda.ui.viewmodel.SongRequestViewModel

/**
 * Ermöglicht Hörern, einen Songwunsch mit Interpret, Titel und
 * optionaler Nachricht an das Redaktionsteam des Senders zu übermitteln.
 */
@Composable
fun SongRequestScreen(service: SongRequestService) {

    val viewModel: SongRequestViewModel = viewModel(
        factory = viewModelFactory { initializer { SongRequestViewModel(service) } }
    )
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = state.artist,
                            onValueChange = viewModel::updateArtist,
                            placeholder = { Text("Interpret*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = state.title,
                            onValueChange = viewModel::updateTitle,
                            placeholder = { Text("Titel*") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = state.message,
                            onValueChange = viewModel::updateMessage,
                            placeholder = { Text("Nachricht (optional)") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }
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
                            text = if (state.isSubmitting) "Wird gesendet…" else "Wunsch absenden",
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

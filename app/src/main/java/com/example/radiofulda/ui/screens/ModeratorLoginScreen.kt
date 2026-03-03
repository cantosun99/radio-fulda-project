package com.example.radiofulda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/** Korrekte PIN für den Moderator-Zugang (Platzhalter). */
private const val MODERATOR_PIN = "123"

/**
 * PIN-Eingabemaske zur Authentifizierung als Moderator.
 * Ruft [onSuccess] auf, sobald die korrekte PIN bestätigt wurde.
 */
@Composable
fun ModeratorLoginScreen(onSuccess: () -> Unit) {
    var pin by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it; showError = false },
                    placeholder = { Text("PIN eingeben") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (pin == MODERATOR_PIN) {
                            onSuccess()
                        } else {
                            showError = true
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Anmelden", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "›",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (showError) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Falsche PIN. Bitte erneut versuchen.",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

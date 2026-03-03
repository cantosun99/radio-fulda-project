package com.example.radiofulda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.radiofulda.data.SongRequest
import com.example.radiofulda.services.SongRequestService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** UI-Zustand für den Screen „Song wünschen". */
data class SongRequestUiState(
    /** Interpret des gewünschten Titels (Pflichtfeld). */
    val artist: String = "",
    /** Titelname des gewünschten Songs (Pflichtfeld). */
    val title: String = "",
    /** Optionale Nachricht des Hörers an das Redaktionsteam. */
    val message: String = "",
    /** `true` während der Wunsch übermittelt wird. */
    val isSubmitting: Boolean = false,
    /** Erfolgsmeldung nach erfolgreicher Übermittlung. */
    val statusMessage: String? = null,
    /** Fehlermeldung bei Validierungs- oder Übermittlungsproblemen. */
    val errorMessage: String? = null
)

/**
 * ViewModel für den Screen „Song wünschen".
 *
 * Validiert die Pflichtfelder Interpret und Titel vor der Übermittlung
 * und sendet den Wunsch über den [SongRequestService] an den Sender.
 */
class SongRequestViewModel(
    private val service: SongRequestService
) : ViewModel() {

    private val _uiState = MutableStateFlow(SongRequestUiState())
    val uiState: StateFlow<SongRequestUiState> = _uiState.asStateFlow()

    /** Aktualisiert das Interpret-Feld und löscht bestehende Meldungen. */
    fun updateArtist(value: String) {
        _uiState.value = _uiState.value.copy(
            artist = value,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Aktualisiert das Titelfeld und löscht bestehende Meldungen. */
    fun updateTitle(value: String) {
        _uiState.value = _uiState.value.copy(
            title = value,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Aktualisiert die optionale Nachricht und löscht bestehende Meldungen. */
    fun updateMessage(value: String) {
        _uiState.value = _uiState.value.copy(
            message = value,
            statusMessage = null,
            errorMessage = null
        )
    }

    /**
     * Validiert die Eingaben und übermittelt den Songwunsch.
     * Zeigt eine Fehlermeldung, wenn Interpret oder Titel fehlen.
     */
    fun submit() {
        val current = _uiState.value
        val a = current.artist.trim()
        val t = current.title.trim()
        val m = current.message.trim().ifEmpty { null }

        if (a.isEmpty() || t.isEmpty()) {
            _uiState.value = current.copy(
                errorMessage = "Bitte mindestens Interpret und Titel ausfüllen."
            )
            return
        }

        try {
            _uiState.value = current.copy(
                isSubmitting = true,
                statusMessage = null,
                errorMessage = null
            )

            service.submitRequest(SongRequest(a, t, m))

            _uiState.value = SongRequestUiState(
                statusMessage = "Danke! Dein Wunsch wurde übermittelt."
            )
        } catch (e: Exception) {
            _uiState.value = current.copy(
                isSubmitting = false,
                errorMessage = e.message ?: "Unbekannter Fehler"
            )
        }
    }
}

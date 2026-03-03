package com.example.radiofulda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.radiofulda.services.PlaylistRatingService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** UI-Zustand für den Screen „Playlist bewerten". */
data class PlaylistRatingUiState(
    /** Aktuell gewählte Sternbewertung (1–5). */
    val selectedRating: Int = 3,
    /** Optionaler Freitext des Hörers. */
    val message: String = "",
    /** `true` während die Bewertung übermittelt wird. */
    val isSubmitting: Boolean = false,
    /** Erfolgsmeldung nach erfolgreicher Übermittlung. */
    val statusMessage: String? = null,
    /** Fehlermeldung bei einem Problem während der Übermittlung. */
    val errorMessage: String? = null
)

/**
 * ViewModel für den Screen „Playlist bewerten".
 *
 * Verwaltet Sternbewertung und optionale Nachricht; sendet das Feedback
 * über den [PlaylistRatingService] an das Sender-Backend.
 */
class PlaylistRatingViewModel(
    private val service: PlaylistRatingService
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaylistRatingUiState())
    val uiState: StateFlow<PlaylistRatingUiState> = _uiState.asStateFlow()

    /** Setzt die Sternbewertung und löscht bestehende Status-/Fehlermeldungen. */
    fun setRating(rating: Int) {
        _uiState.value = _uiState.value.copy(
            selectedRating = rating,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Aktualisiert den Freitext und löscht bestehende Status-/Fehlermeldungen. */
    fun updateMessage(value: String) {
        _uiState.value = _uiState.value.copy(
            message = value,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Übermittelt die Bewertung über den Service und aktualisiert den UI-Zustand. */
    fun submit() {
        val current = _uiState.value
        val message = current.message.trim().ifEmpty { null }
        try {
            _uiState.value = current.copy(
                isSubmitting = true,
                statusMessage = null,
                errorMessage = null
            )

            service.submitRating(current.selectedRating, message)

            _uiState.value = current.copy(
                isSubmitting = false,
                statusMessage = "Danke! Deine Bewertung (${current.selectedRating}/5) wurde gesendet."
            )
        } catch (e: Exception) {
            _uiState.value = current.copy(
                isSubmitting = false,
                errorMessage = e.message ?: "Unbekannter Fehler"
            )
        }
    }
}

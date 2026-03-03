package com.example.radiofulda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.radiofulda.data.ModeratorRating
import com.example.radiofulda.services.ModeratorRatingService
import com.example.radiofulda.services.ModeratorService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** UI-Zustand für den Screen „Moderator bewerten". */
data class ModeratorRatingUiState(
    /** Liste aller aktiven Moderatoren zur Auswahl. */
    val moderators: List<String> = emptyList(),
    /** Index des aktuell ausgewählten Moderators in [moderators]. */
    val selectedIndex: Int = 0,
    /** Aktuell gewählte Sternbewertung (1–5). */
    val selectedRating: Int = 3,
    /** Optionaler Freitext-Kommentar des Hörers. */
    val comment: String = "",
    /** `true` während die Bewertung übermittelt wird. */
    val isSubmitting: Boolean = false,
    /** Erfolgsmeldung nach erfolgreicher Übermittlung. */
    val statusMessage: String? = null,
    /** Fehlermeldung bei Validierungs- oder Übermittlungsproblemen. */
    val errorMessage: String? = null
)

/**
 * ViewModel für den Screen „Moderator bewerten".
 *
 * Lädt die aktiven Moderatoren vom [ModeratorService] und koordiniert die
 * Abgabe einer Sternbewertung mit optionalem Kommentar über den [ModeratorRatingService].
 */
class ModeratorRatingViewModel(
    private val service: ModeratorRatingService,
    private val moderatorService: ModeratorService
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ModeratorRatingUiState(moderators = moderatorService.getModerators())
    )
    val uiState: StateFlow<ModeratorRatingUiState> = _uiState.asStateFlow()

    /** Wählt den Moderator am angegebenen Index aus. */
    fun selectModerator(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedIndex = index,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Setzt die Sternbewertung und löscht bestehende Meldungen. */
    fun setRating(rating: Int) {
        _uiState.value = _uiState.value.copy(
            selectedRating = rating,
            statusMessage = null,
            errorMessage = null
        )
    }

    /** Aktualisiert den Freitext-Kommentar und löscht bestehende Meldungen. */
    fun updateComment(value: String) {
        _uiState.value = _uiState.value.copy(
            comment = value,
            statusMessage = null,
            errorMessage = null
        )
    }

    /**
     * Validiert die Bewertung und übermittelt sie über den Service.
     * Zeigt eine Fehlermeldung, wenn keine gültige Sternbewertung gewählt wurde.
     */
    fun submit() {
        val current = _uiState.value

        if (current.selectedRating !in 1..5) {
            _uiState.value = current.copy(
                errorMessage = "Bitte eine Bewertung (1–5) auswählen."
            )
            return
        }

        val moderatorName = current.moderators.getOrNull(current.selectedIndex) ?: "Unbekannt"

        try {
            _uiState.value = current.copy(
                isSubmitting = true,
                statusMessage = null,
                errorMessage = null
            )

            service.submitRating(
                ModeratorRating(
                    moderatorName = moderatorName,
                    rating = current.selectedRating,
                    comment = current.comment.trim().ifEmpty { null }
                )
            )

            _uiState.value = current.copy(
                isSubmitting = false,
                statusMessage = "Danke! Bewertung übermittelt."
            )
        } catch (e: Exception) {
            _uiState.value = current.copy(
                isSubmitting = false,
                errorMessage = e.message ?: "Unbekannter Fehler"
            )
        }
    }
}

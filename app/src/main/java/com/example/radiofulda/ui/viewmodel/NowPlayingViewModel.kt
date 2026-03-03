package com.example.radiofulda.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radiofulda.data.NowPlayingInfo
import com.example.radiofulda.services.NowPlayingService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/** UI-Zustand für den Screen „Aktueller Titel". */
data class NowPlayingUiState(
    /** `true` während der erste Titelabruf läuft. */
    val isLoading: Boolean = false,
    /** Aktuell abgespielter Titel, oder `null` wenn noch nicht geladen. */
    val track: NowPlayingInfo? = null,
    /** Verbleibende Spielzeit des aktuellen Titels in Sekunden. */
    val remainingSec: Int = 0,
    /** Fehlermeldung, falls der Abruf fehlschlägt. */
    val error: String? = null
)

/**
 * ViewModel für den Screen „Aktueller Titel".
 *
 * Lädt beim Start den aktuell laufenden Titel und startet einen Sekundencount-down.
 * Läuft die Zeit ab, wird automatisch der Folgetitel geladen.
 * Wechselt der Moderator das Sendungsalbum, wird der aktuelle Track sofort aktualisiert.
 *
 * Das ViewModel ist Activity-scoped und überlebt daher Navigationsänderungen.
 */
class NowPlayingViewModel(
    private val service: NowPlayingService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NowPlayingUiState(isLoading = true))
    val uiState: StateFlow<NowPlayingUiState> = _uiState.asStateFlow()

    /** Referenz auf den laufenden Countdown-Coroutine-Job. */
    private var countdownJob: Job? = null

    init {
        // Initial den aktuellen Titel laden
        loadTrack { service.getNowPlaying() }

        // Auf Albumwechsel durch den Moderator reagieren; drop(1) überspringt
        // den aktuellen StateFlow-Wert, sodass nur zukünftige Änderungen auslösen
        viewModelScope.launch {
            service.selectedAlbum
                .drop(1)
                .filterNotNull()
                .collect { loadTrack { service.getNowPlaying() } }
        }
    }

    /** Lädt einen Titel via [fetch], aktualisiert den UI-Zustand und startet den Countdown. */
    private fun loadTrack(fetch: () -> NowPlayingInfo) {
        countdownJob?.cancel()
        viewModelScope.launch {
            try {
                _uiState.value = NowPlayingUiState(isLoading = true)
                val track = fetch()
                _uiState.value = NowPlayingUiState(track = track, remainingSec = track.remainingSec)
                startCountdown()
            } catch (e: Exception) {
                _uiState.value = NowPlayingUiState(error = e.message ?: "Fehler")
            }
        }
    }

    /**
     * Startet den sekundenweisen Countdown für den aktuellen Titel.
     * Erreicht [NowPlayingUiState.remainingSec] null, wird automatisch der nächste Titel geladen.
     */
    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val remaining = _uiState.value.remainingSec - 1
                if (remaining <= 0) {
                    loadTrack { service.getNextTrack() }
                    break
                } else {
                    _uiState.value = _uiState.value.copy(remainingSec = remaining)
                }
            }
        }
    }
}

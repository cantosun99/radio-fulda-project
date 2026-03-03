package com.example.radiofulda.services

import com.example.radiofulda.data.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Verwaltet das aktuell vom Moderator ausgewählte Sendungsalbum.
 *
 * Das gewählte Album wird als [StateFlow] bereitgestellt, sodass der
 * [NowPlayingService] reaktiv auf Änderungen reagieren kann und sofort
 * zur ersten Spur des neuen Albums wechselt.
 */
class PlaylistSelectionService {

    private val _selectedAlbum = MutableStateFlow<Album?>(null)

    /** Das aktuell ausgewählte Sendungsalbum, oder `null` wenn keines gesetzt ist. */
    val selectedAlbum: StateFlow<Album?> = _selectedAlbum.asStateFlow()

    /**
     * Setzt das aktive Sendungsalbum.
     * @param album Das vom Moderator gewählte Album.
     */
    fun selectAlbum(album: Album) {
        _selectedAlbum.value = album
    }

}

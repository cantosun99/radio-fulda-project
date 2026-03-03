package com.example.radiofulda.services

import com.example.radiofulda.data.Album
import com.example.radiofulda.data.NowPlayingInfo
import com.example.radiofulda.services.api.NowPlayingApi
import kotlinx.coroutines.flow.StateFlow

/**
 * Koordiniert den Abruf von Titelinformationen.
 *
 * Priorisiert das vom Moderator ausgewählte Album: Ist ein Album aktiv,
 * werden dessen Tracks sequenziell abgespielt. Andernfalls wird die
 * [NowPlayingApi] des Senders genutzt, um den aktuellen Titel abzufragen.
 */
class NowPlayingService(
    private val api: NowPlayingApi,
    private val playlistSelection: PlaylistSelectionService
) {
    /** Aktuell vom Moderator gewähltes Album; `null` bedeutet Senderbetrieb. */
    val selectedAlbum: StateFlow<Album?> = playlistSelection.selectedAlbum

    /** Index des zuletzt abgespielten Tracks im gewählten Album. */
    private var currentTrackIndex = 0

    /**
     * Gibt den aktuell laufenden Titel zurück.
     * Ist ein Album gewählt, startet die Wiedergabe bei dessen erstem Track.
     */
    fun getNowPlaying(): NowPlayingInfo {
        currentTrackIndex = 0
        return playlistSelection.selectedAlbum.value
            ?.let { albumTrackToNowPlaying(it, 0) }
            ?: api.fetchNowPlaying()
    }

    /**
     * Wechselt zum nächsten Titel und gibt ihn zurück.
     * Im Album-Modus wird der Index zyklisch erhöht.
     */
    fun getNextTrack(): NowPlayingInfo {
        val album = playlistSelection.selectedAlbum.value
        return if (album != null) {
            currentTrackIndex = (currentTrackIndex + 1) % album.tracks.size
            albumTrackToNowPlaying(album, currentTrackIndex)
        } else {
            api.fetchNextTrack()
        }
    }

    /** Konvertiert einen Album-Track in ein [NowPlayingInfo]-Objekt. */
    private fun albumTrackToNowPlaying(album: Album, index: Int): NowPlayingInfo {
        val track = album.tracks[index]
        return NowPlayingInfo(
            artist = album.artist,
            album = album.title,
            title = track.title,
            durationSec = track.durationSec,
            remainingSec = track.durationSec,
            coverResId = album.coverResId
        )
    }
}

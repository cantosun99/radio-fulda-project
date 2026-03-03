package com.example.radiofulda.services.api

import com.example.radiofulda.data.NowPlayingInfo

/**
 * Schnittstelle zum Titelinfo-System des Senders.
 * Liefert Metadaten zum aktuell gespielten und zum nächsten Titel.
 */
interface NowPlayingApi {
    /** Fragt den aktuell laufenden Titel mit allen Metadaten ab. */
    fun fetchNowPlaying(): NowPlayingInfo

    /** Wechselt zum nächsten Titel und gibt dessen Metadaten zurück. */
    fun fetchNextTrack(): NowPlayingInfo
}

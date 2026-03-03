package com.example.radiofulda.services.api.stub

import com.example.radiofulda.R
import com.example.radiofulda.data.NowPlayingInfo
import com.example.radiofulda.services.api.NowPlayingApi

/**
 * Stub-Implementierung von [NowPlayingApi].
 *
 * Simuliert das Titelinfo-System des Senders mit statischen Testdaten.
 * Gibt Tracks aus dem Album „Paranoid" von Black Sabbath in sequenzieller
 * Reihenfolge zurück. Im Produktivbetrieb würden die Daten per HTTP oder
 * einem internen Broadcast-System des Senders abgefragt.
 */
class StubNowPlayingApi : NowPlayingApi {

    private data class TrackEntry(val title: String, val durationSec: Int)

    /** Statische Trackliste des Albums „Paranoid". */
    private val tracks = listOf(
        TrackEntry("War Pigs",           477),
        TrackEntry("Paranoid",           168),
        TrackEntry("Planet Caravan",     272),
        TrackEntry("Iron Man",           356),
        TrackEntry("Electric Funeral",   293),
        TrackEntry("Hand of Doom",       428),
        TrackEntry("Rat Salad",          150),
        TrackEntry("Fairies Wear Boots", 375)
    )

    /** Index des aktuell simulierten Titels. */
    private var currentIndex = 0

    /** Gibt den aktuell simulierten Titel zurück. */
    override fun fetchNowPlaying(): NowPlayingInfo = makeInfo(currentIndex)

    /** Wechselt zum nächsten Track in der Reihenfolge und gibt ihn zurück. */
    override fun fetchNextTrack(): NowPlayingInfo {
        currentIndex = (currentIndex + 1) % tracks.size
        return makeInfo(currentIndex)
    }

    /** Erstellt ein [NowPlayingInfo]-Objekt für den Track am angegebenen Index. */
    private fun makeInfo(index: Int): NowPlayingInfo {
        val track = tracks[index]
        return NowPlayingInfo(
            artist = "Black Sabbath",
            album = "Paranoid",
            title = track.title,
            durationSec = track.durationSec,
            remainingSec = track.durationSec,
            coverResId = R.drawable.cover_paranoid
        )
    }
}

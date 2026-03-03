package com.example.radiofulda.data

/** Enthält alle vom Sender gelieferten Metadaten zum aktuell gespielten Titel. */
data class NowPlayingInfo(
    /** Interpret des Titels. */
    val artist: String,
    /** Albumtitel, dem der Song entstammt. */
    val album: String,
    /** Name des Songs. */
    val title: String,
    /** Gesamtlaufzeit des Titels in Sekunden. */
    val durationSec: Int,
    /** Verbleibende Spielzeit in Sekunden zum Zeitpunkt des letzten API-Abrufs. */
    val remainingSec: Int,
    /** Ressourcen-ID des Album-Cover-Drawables. */
    val coverResId: Int
)

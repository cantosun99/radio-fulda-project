package com.example.radiofulda.data

/** Repräsentiert einen einzelnen Titel innerhalb eines Albums. */
data class Track(
    /** Titelname des Songs. */
    val title: String,
    /** Laufzeit des Songs in Sekunden. */
    val durationSec: Int
)

/** Repräsentiert ein Album im Sendekatalog des Senders. */
data class Album(
    /** Eindeutige Kennung des Albums. */
    val id: String,
    /** Künstlername. */
    val artist: String,
    /** Albumtitel. */
    val title: String,
    /** Ressourcen-ID des Album-Cover-Drawables. */
    val coverResId: Int,
    /** Vollständige Titelliste des Albums. */
    val tracks: List<Track>
)

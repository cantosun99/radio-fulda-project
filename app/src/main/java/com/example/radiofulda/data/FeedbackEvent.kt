package com.example.radiofulda.data

/** Klassifiziert die Art einer eingehenden Hörer-Rückmeldung. */
enum class EventType {
    /** Bewertung der aktuellen Playlist. */
    PLAYLIST_RATING,
    /** Songwunsch eines Hörers. */
    SONG_REQUEST,
    /** Bewertung eines Moderators. */
    MODERATOR_RATING
}

/**
 * Repräsentiert eine Hörer-Rückmeldung, die über den [EventBus] an den
 * Moderator-Bereich weitergeleitet und im Live-Feed angezeigt wird.
 */
data class FeedbackEvent(
    /** Lesbarer Text der Rückmeldung, inklusive optionaler Nutzernachricht. */
    val message: String,
    /** Art der Rückmeldung zur Kategorisierung im Live-Feed. */
    val type: EventType
)

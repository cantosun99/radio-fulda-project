package com.example.radiofulda.services.api

import com.example.radiofulda.data.ModeratorRating

/**
 * Schnittstelle zum Feedback-System des Senders.
 * Übermittelt Hörer-Bewertungen zur Persistierung und Weiterverarbeitung an das Backend.
 */
interface FeedbackApi {
    /**
     * Sendet eine Playlist-Bewertung mit optionaler Nachricht.
     * @param rating Sternbewertung des Hörers (1–5).
     * @param message Optionaler Freitext des Hörers.
     */
    fun submitPlaylistRating(rating: Int, message: String?)

    /**
     * Sendet eine Moderator-Bewertung.
     * @param rating Bewertungsobjekt mit Moderatorname, Sterne und optionalem Kommentar.
     */
    fun submitModeratorRating(rating: ModeratorRating)
}

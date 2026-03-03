package com.example.radiofulda.services.api.stub

import com.example.radiofulda.data.ModeratorRating
import com.example.radiofulda.services.api.FeedbackApi

/**
 * Stub-Implementierung von [FeedbackApi].
 *
 * Simuliert die Anbindung an ein reales Feedback-Backend des Senders.
 * Im Produktivbetrieb würden Bewertungen hier per HTTP, gRPC o. Ä.
 * persistiert und für Auswertungen weiterverarbeitet.
 */
class StubFeedbackApi : FeedbackApi {

    /** Stub: Playlist-Bewertung wird nicht persistent gespeichert. */
    override fun submitPlaylistRating(rating: Int, message: String?) {
        // Produktiv: HTTP POST an das Feedback-Backend des Senders
    }

    /** Stub: Moderator-Bewertung wird nicht persistent gespeichert. */
    override fun submitModeratorRating(rating: ModeratorRating) {
        // Produktiv: HTTP POST an das Feedback-Backend des Senders
    }
}

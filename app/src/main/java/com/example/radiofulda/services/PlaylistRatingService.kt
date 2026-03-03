package com.example.radiofulda.services

import com.example.radiofulda.data.EventType
import com.example.radiofulda.data.FeedbackEvent
import com.example.radiofulda.services.api.FeedbackApi
import com.example.radiofulda.services.api.ModeratorAlertApi

/**
 * Verarbeitet die Abgabe einer Playlist-Bewertung durch einen Hörer.
 *
 * Leitet Sternbewertung und optionale Nachricht an [FeedbackApi] weiter,
 * veröffentlicht ein [FeedbackEvent] im [EventBus] für den Moderator-Live-Feed
 * und löst über [ModeratorAlertApi] eine Echtzeit-Benachrichtigung aus.
 */
class PlaylistRatingService(
    private val feedbackApi: FeedbackApi,
    private val alertApi: ModeratorAlertApi
) {
    /**
     * Sendet die Bewertung und informiert den Moderator.
     * @param rating Sternbewertung des Hörers (1–5).
     * @param message Optionaler Freitext des Hörers.
     */
    fun submitRating(rating: Int, message: String? = null) {
        feedbackApi.submitPlaylistRating(rating, message)

        val text = buildString {
            append("Playlist wurde mit $rating Stern(en) bewertet.")
            if (!message.isNullOrBlank()) append("\n\"$message\"")
        }
        val event = FeedbackEvent(message = text, type = EventType.PLAYLIST_RATING)

        EventBus.publish(event)
        alertApi.notifyModerators(event)
    }
}

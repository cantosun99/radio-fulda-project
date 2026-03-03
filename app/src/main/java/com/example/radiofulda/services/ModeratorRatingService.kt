package com.example.radiofulda.services

import com.example.radiofulda.data.EventType
import com.example.radiofulda.data.FeedbackEvent
import com.example.radiofulda.data.ModeratorRating
import com.example.radiofulda.services.api.FeedbackApi
import com.example.radiofulda.services.api.ModeratorAlertApi

/**
 * Verarbeitet die Abgabe einer Moderator-Bewertung durch einen Hörer.
 *
 * Leitet die Bewertung an [FeedbackApi] weiter, veröffentlicht ein [FeedbackEvent]
 * im [EventBus] für den Moderator-Live-Feed und löst über [ModeratorAlertApi]
 * eine Echtzeit-Benachrichtigung aus.
 */
class ModeratorRatingService(
    private val feedbackApi: FeedbackApi,
    private val alertApi: ModeratorAlertApi
) {
    /**
     * Sendet die Moderator-Bewertung und informiert den Moderator.
     * @param rating Bewertungsobjekt mit Name, Sterne und optionalem Kommentar.
     */
    fun submitRating(rating: ModeratorRating) {
        feedbackApi.submitModeratorRating(rating)

        val text = buildString {
            append("Moderator ${rating.moderatorName} wurde mit ${rating.rating} Stern(en) bewertet.")
            if (!rating.comment.isNullOrBlank()) append("\n\"${rating.comment}\"")
        }
        val event = FeedbackEvent(message = text, type = EventType.MODERATOR_RATING)

        EventBus.publish(event)
        alertApi.notifyModerators(event)
    }
}

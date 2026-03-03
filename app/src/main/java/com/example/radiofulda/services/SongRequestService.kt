package com.example.radiofulda.services

import com.example.radiofulda.data.EventType
import com.example.radiofulda.data.FeedbackEvent
import com.example.radiofulda.data.SongRequest
import com.example.radiofulda.services.api.ModeratorAlertApi
import com.example.radiofulda.services.api.SongRequestApi

/**
 * Verarbeitet einen Songwunsch eines Hörers.
 *
 * Übermittelt den Wunsch an das [SongRequestApi]-System des Senders,
 * veröffentlicht ein [FeedbackEvent] im [EventBus] für den Moderator-Live-Feed
 * und informiert Moderatoren über die [ModeratorAlertApi].
 */
class SongRequestService(
    private val api: SongRequestApi,
    private val alertApi: ModeratorAlertApi
) {
    /**
     * Übermittelt den Songwunsch und informiert den Moderator.
     * @param request Wunschdaten mit Interpret, Titel und optionaler Nachricht.
     */
    fun submitRequest(request: SongRequest) {
        api.submitSongRequest(request)

        val text = buildString {
            append("Songwunsch: ${request.artist} – ${request.title}")
            if (!request.message.isNullOrBlank()) append("\n\"${request.message}\"")
        }
        val event = FeedbackEvent(message = text, type = EventType.SONG_REQUEST)

        EventBus.publish(event)
        alertApi.notifyModerators(event)
    }
}

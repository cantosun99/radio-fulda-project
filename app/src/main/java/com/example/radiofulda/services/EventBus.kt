package com.example.radiofulda.services

import com.example.radiofulda.data.FeedbackEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Applikationsweiter Event-Bus für eingehende Hörer-Rückmeldungen.
 *
 * Neue Events werden vorne in die Liste eingefügt, sodass der Live-Feed
 * immer die neueste Rückmeldung zuerst anzeigt. Abonnenten (z. B. der
 * Moderator-Live-Screen) erhalten alle Änderungen reaktiv über [events].
 */
object EventBus {

    private val _events = MutableStateFlow<List<FeedbackEvent>>(emptyList())

    /** Aktueller Live-Feed aller eingegangenen Rückmeldungen, neueste zuerst. */
    val events: StateFlow<List<FeedbackEvent>> = _events.asStateFlow()

    /**
     * Veröffentlicht ein neues Feedback-Ereignis im Bus.
     * Das Ereignis wird an den Anfang der Liste gestellt.
     */
    fun publish(event: FeedbackEvent) {
        _events.value = listOf(event) + _events.value
    }
}

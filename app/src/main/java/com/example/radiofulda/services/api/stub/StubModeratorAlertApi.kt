package com.example.radiofulda.services.api.stub

import com.example.radiofulda.data.FeedbackEvent
import com.example.radiofulda.services.api.ModeratorAlertApi

/**
 * Stub-Implementierung von [ModeratorAlertApi].
 *
 * Simuliert ein Push-/WebSocket-basiertes Benachrichtigungssystem des Senders.
 * Im Produktivbetrieb würde hier eine Echtzeit-Benachrichtigung an alle
 * aktiven Moderatoren ausgelöst (z. B. über Firebase Cloud Messaging,
 * WebSocket oder eine interne Broadcast-API des Senders).
 */
class StubModeratorAlertApi : ModeratorAlertApi {

    /** Stub: Moderatoren werden nicht benachrichtigt. */
    override fun notifyModerators(event: FeedbackEvent) {
        // Produktiv: Push-Benachrichtigung / WebSocket-Event an aktive Moderatoren
    }
}

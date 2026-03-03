package com.example.radiofulda.services.api

import com.example.radiofulda.data.FeedbackEvent

/**
 * Schnittstelle zum Benachrichtigungssystem des Senders.
 * Informiert Moderatoren in Echtzeit über eingehende Hörer-Rückmeldungen.
 */
interface ModeratorAlertApi {
    /**
     * Benachrichtigt alle aktiven Moderatoren über ein neues Feedback-Ereignis.
     * @param event Das zu meldende Ereignis mit Inhalt und Typ.
     */
    fun notifyModerators(event: FeedbackEvent)
}

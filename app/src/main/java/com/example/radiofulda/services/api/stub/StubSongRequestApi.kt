package com.example.radiofulda.services.api.stub

import com.example.radiofulda.data.SongRequest
import com.example.radiofulda.services.api.SongRequestApi

/**
 * Stub-Implementierung von [SongRequestApi].
 *
 * Simuliert die Übermittlung von Songwünschen an das Redaktionssystem des Senders.
 * Im Produktivbetrieb würde der Wunsch persistiert und dem Moderator
 * in der Redaktionssoftware zur Verfügung gestellt.
 */
class StubSongRequestApi : SongRequestApi {

    /** Stub: Songwunsch wird nicht weitergeleitet. */
    override fun submitSongRequest(request: SongRequest) {
        // Produktiv: HTTP POST an das Redaktionssystem des Senders
    }
}

package com.example.radiofulda.services.api

import com.example.radiofulda.data.SongRequest

/**
 * Schnittstelle zum Songwunsch-System des Senders.
 * Leitet Hörerwünsche zur Bearbeitung an das Redaktionssystem weiter.
 */
interface SongRequestApi {
    /**
     * Übermittelt einen Songwunsch an das Redaktionssystem.
     * @param request Wunschdaten mit Interpret, Titel und optionaler Nachricht.
     */
    fun submitSongRequest(request: SongRequest)
}

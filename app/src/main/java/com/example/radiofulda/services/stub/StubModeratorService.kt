package com.example.radiofulda.services.stub

import com.example.radiofulda.services.ModeratorService

/**
 * Stub-Implementierung von [ModeratorService].
 *
 * Liefert eine fest kodierte Liste von Moderatoren-Namen für Testzwecke.
 * Im Produktivbetrieb würde die aktuelle Moderatorenliste dynamisch
 * vom Personalverwaltungssystem des Senders abgerufen.
 */
class StubModeratorService : ModeratorService {

    /** Gibt eine statische Testliste mit vier Moderatoren-Namen zurück. */
    override fun getModerators() = listOf("Anna", "Ben", "Cem", "Daria")
}

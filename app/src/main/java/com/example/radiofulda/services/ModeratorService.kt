package com.example.radiofulda.services

/**
 * Schnittstelle zum Moderatoren-Verwaltungssystem des Senders.
 * Liefert die aktuell aktiven Moderatoren für die Bewertungsauswahl durch Hörer.
 */
interface ModeratorService {
    /** Gibt eine Liste der Namen aller aktiven Moderatoren zurück. */
    fun getModerators(): List<String>
}

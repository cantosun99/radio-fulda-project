package com.example.radiofulda.data

/** Enthält die Bewertungsdaten eines Hörers für einen Moderator. */
data class ModeratorRating(
    /** Name des bewerteten Moderators. */
    val moderatorName: String,
    /** Sternbewertung des Hörers (1–5). */
    val rating: Int,
    /** Optionaler Freitext-Kommentar des Hörers. */
    val comment: String?
)

package com.example.radiofulda.data

/** Enthält die Daten eines Songwunschs, der an den Sender übermittelt werden soll. */
data class SongRequest(
    /** Interpret des gewünschten Titels. */
    val artist: String,
    /** Name des gewünschten Songs. */
    val title: String,
    /** Optionale Nachricht des Hörers an das Redaktionsteam. */
    val message: String?
)

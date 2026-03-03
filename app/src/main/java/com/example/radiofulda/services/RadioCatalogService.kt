package com.example.radiofulda.services

import com.example.radiofulda.data.Album

/**
 * Schnittstelle zum Sendekatalog des Senders.
 * Stellt die verfügbaren Alben für die Moderator-Playlist-Auswahl bereit.
 */
interface RadioCatalogService {
    /** Gibt alle verfügbaren Alben des Sendekatalogs zurück. */
    fun getAlbums(): List<Album>

    /**
     * Sucht ein einzelnes Album anhand seiner eindeutigen Kennung.
     * @param id Die Album-ID.
     * @return Das gefundene Album oder `null`, falls nicht vorhanden.
     */
    fun getAlbumById(id: String): Album?
}

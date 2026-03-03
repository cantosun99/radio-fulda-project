package com.example.radiofulda.services.stub

import com.example.radiofulda.R
import com.example.radiofulda.data.Album
import com.example.radiofulda.data.Track
import com.example.radiofulda.services.RadioCatalogService

/**
 * Stub-Implementierung von [RadioCatalogService].
 *
 * Stellt einen statischen Testkatalog mit vier Black-Sabbath-Alben bereit,
 * inklusive vollständiger Tracklisten und Laufzeiten. Im Produktivbetrieb
 * würde der Katalog über eine Katalog-API des Senders dynamisch bezogen.
 */
class StubRadioCatalogService : RadioCatalogService {

    /** Gibt den vollständigen statischen Testkatalog zurück. */
    override fun getAlbums(): List<Album> {
        return listOf(

            Album(
                id = "1",
                artist = "Black Sabbath",
                title = "Black Sabbath",
                coverResId = R.drawable.cover_black_sabbath,
                tracks = listOf(
                    Track("Black Sabbath",            380),
                    Track("The Wizard",               264),
                    Track("Behind the Wall of Sleep", 217),
                    Track("N.I.B.",                   368),
                    Track("Evil Woman",               205),
                    Track("Sleeping Village",         226),
                    Track("Warning",                  628)
                )
            ),

            Album(
                id = "2",
                artist = "Black Sabbath",
                title = "Paranoid",
                coverResId = R.drawable.cover_paranoid,
                tracks = listOf(
                    Track("War Pigs",           477),
                    Track("Paranoid",           168),
                    Track("Planet Caravan",     272),
                    Track("Iron Man",           356),
                    Track("Electric Funeral",   293),
                    Track("Hand of Doom",       428),
                    Track("Rat Salad",          150),
                    Track("Fairies Wear Boots", 375)
                )
            ),

            Album(
                id = "3",
                artist = "Black Sabbath",
                title = "Master of Reality",
                coverResId = R.drawable.cover_master_of_reality,
                tracks = listOf(
                    Track("Sweet Leaf",             305),
                    Track("After Forever",          327),
                    Track("Embryo",                  28),
                    Track("Children of the Grave",  318),
                    Track("Orchid",                  91),
                    Track("Lord of This World",     327),
                    Track("Solitude",               302),
                    Track("Into the Void",          373)
                )
            ),

            Album(
                id = "4",
                artist = "Black Sabbath",
                title = "Vol. 4",
                coverResId = R.drawable.cover_vol_4,
                tracks = listOf(
                    Track("Wheels of Confusion", 482),
                    Track("Tomorrow's Dream",    192),
                    Track("Changes",             284),
                    Track("FX",                  104),
                    Track("Supernaut",           290),
                    Track("Snowblind",           333),
                    Track("Cornucopia",          235),
                    Track("Laguna Sunrise",      173),
                    Track("St. Vitus Dance",     150),
                    Track("Under the Sun",       353)
                )
            )
        )
    }

    /** Sucht ein Album anhand seiner [id] im Katalog. Gibt `null` zurück, falls nicht gefunden. */
    override fun getAlbumById(id: String): Album? = getAlbums().find { it.id == id }
}

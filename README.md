# Radio Fulda

**[English](#english) | [Deutsch](#deutsch)**

---

## English

A native Android companion app for a local radio station that bridges the gap between listeners and moderators in real time.

### Problem & Solution

Local radio stations often lack a direct feedback channel between their audience and on-air moderators. This app addresses that by giving listeners a way to interact with the broadcast, view what's playing, rate content, and make requests, while giving moderators an instant live overview of all incoming feedback.

All external radio station systems (track metadata, ratings relay, request forwarding) are integrated via **stub interfaces**, so the app works standalone and can be connected to real backend systems at any time without changing the UI or business logic.

### Features

#### Listeners
- **Now Playing** - See the current track's artist, title, and album, with a live progress bar that auto-advances through the playlist
- **Playlist Rating** - Rate the active playlist with up to 5 stars and optional written feedback
- **Song Request** - Submit a song request (artist + title) with an optional message to the editorial team
- **Moderator Rating** - Rate the on-air moderator with stars and an optional comment

#### Moderators (PIN-protected)
- **Playlist Selection** - Browse the album catalog and set the active broadcast playlist
- **Live Feedback Feed** - Real-time dashboard showing all incoming listener ratings, requests, and comments as they arrive

The moderator area is protected by a PIN. The demo PIN is **123**.

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material Design 3 |
| Architecture | MVVM, StateFlow |
| Navigation | Navigation Compose |
| Build | Gradle KTS |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

### Architecture

```
app/src/main/java/com/example/radiofulda/
├── data/           # Data classes (Album, Track, SongRequest, ...)
├── services/
│   ├── api/        # Interface definitions for all external systems
│   └── stub/       # Stub implementations with demo data
├── ui/
│   ├── screens/    # Compose UI screens
│   ├── viewmodel/  # MVVM ViewModels with StateFlow state
│   └── theme/      # Material 3 theme & color scheme
└── MainActivity.kt # Entry point & navigation graph
```

Key patterns:
- **MVVM** with `StateFlow` for fully reactive UI
- **Interface-based stub layer** - every external system (now-playing API, feedback relay, etc.) is hidden behind an interface with a stub implementation, ready to be swapped for a real backend
- **Event Bus** to decouple listener feedback publishing from the moderator live feed
- **Activity-scoped ViewModel** for `NowPlayingViewModel` so playback state persists across navigation

### Getting Started

Open the project in Android Studio and press **Run**.

---

## Deutsch

Eine native Android-App für einen lokalen Radiosender, die Hörer und Moderatoren in Echtzeit miteinander verbindet.

### Problem & Lösung

Lokalen Radiosendern fehlt häufig ein direkter Feedback-Kanal zwischen Publikum und den Moderatoren im Studio. Diese App schließt diese Lücke: Hörer können das laufende Programm verfolgen, Inhalte bewerten und Wünsche einreichen, während Moderatoren alle eingehenden Rückmeldungen sofort in einem Live-Feed einsehen können.

Alle externen Sendersysteme (Titelmetadaten, Weitergabe von Bewertungen, Weiterleitung von Wünschen) sind über **Stub-Interfaces** eingebunden. Die App läuft damit eigenständig und kann jederzeit ohne Änderungen an UI oder Geschäftslogik an echte Backend-Systeme angeschlossen werden.

### Funktionen

#### Hörer
- **Aktueller Titel** - Interpret, Titel und Album mit einem Live-Fortschrittsbalken, der automatisch zum nächsten Track weiterspringt
- **Playlist bewerten** - Bis zu 5 Sterne und optionaler Freitextkommentar zur aktuellen Playlist
- **Song wünschen** - Wunschsong (Interpret + Titel) mit optionaler Nachricht an die Redaktion einreichen
- **Moderator bewerten** - Aktuellen Moderator mit Sternen und optionalem Kommentar bewerten

#### Moderatoren (PIN-geschützt)
- **Playlist auswählen** - Albumkatalog durchsuchen und die aktive Sendungs-Playlist festlegen
- **Live-Feedback-Feed** - Echtzeit-Dashboard mit allen eingehenden Bewertungen, Wünschen und Kommentaren

Der Moderatorenbereich ist PIN-geschützt. Der Demo-PIN lautet **123**.

### Technologie

| Schicht | Technologie |
|---|---|
| Sprache | Kotlin |
| UI | Jetpack Compose, Material Design 3 |
| Architektur | MVVM, StateFlow |
| Navigation | Navigation Compose |
| Build | Gradle KTS |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

### Architektur

```
app/src/main/java/com/example/radiofulda/
├── data/           # Datenklassen (Album, Track, SongRequest, ...)
├── services/
│   ├── api/        # Interface-Definitionen für alle externen Systeme
│   └── stub/       # Stub-Implementierungen mit Demo-Daten
├── ui/
│   ├── screens/    # Compose-UI-Screens
│   ├── viewmodel/  # MVVM-ViewModels mit StateFlow-State
│   └── theme/      # Material-3-Theme & Farbschema
└── MainActivity.kt # Einstiegspunkt & Navigations-Graph
```

Wesentliche Muster:
- **MVVM** mit `StateFlow` für vollständig reaktive UI
- **Interface-basierte Stub-Schicht** - jedes externe System (Now-Playing-API, Feedback-Relay usw.) ist hinter einem Interface abstrahiert und durch eine echte Implementierung austauschbar
- **Event Bus** zur losen Kopplung zwischen Feedback-Versand und Moderator-Live-Feed
- **Activity-scoped ViewModel** für `NowPlayingViewModel`, damit der Wiedergabe-Zustand beim Navigieren erhalten bleibt

### Quickstart

Projekt in Android Studio öffnen und **Run** drücken.
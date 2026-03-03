package com.example.radiofulda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.radiofulda.services.ModeratorRatingService
import com.example.radiofulda.services.NowPlayingService
import com.example.radiofulda.services.PlaylistRatingService
import com.example.radiofulda.services.PlaylistSelectionService
import com.example.radiofulda.services.RadioCatalogService
import com.example.radiofulda.services.SongRequestService
import com.example.radiofulda.services.api.stub.StubFeedbackApi
import com.example.radiofulda.services.api.stub.StubModeratorAlertApi
import com.example.radiofulda.services.api.stub.StubNowPlayingApi
import com.example.radiofulda.services.api.stub.StubSongRequestApi
import com.example.radiofulda.services.stub.StubModeratorService
import com.example.radiofulda.services.stub.StubRadioCatalogService
import com.example.radiofulda.ui.screens.HomeScreen
import com.example.radiofulda.ui.screens.ModeratorLiveScreen
import com.example.radiofulda.ui.screens.ModeratorLoginScreen
import com.example.radiofulda.ui.screens.ModeratorMenuScreen
import com.example.radiofulda.ui.screens.ModeratorPlaylistSelectScreen
import com.example.radiofulda.ui.screens.ModeratorRatingScreen
import com.example.radiofulda.ui.screens.NowPlayingScreen
import com.example.radiofulda.ui.screens.PlaylistRatingScreen
import com.example.radiofulda.ui.screens.SongRequestScreen
import com.example.radiofulda.ui.theme.RadioFuldaTheme
import com.example.radiofulda.ui.viewmodel.NowPlayingUiState
import com.example.radiofulda.ui.viewmodel.NowPlayingViewModel

private val TopBarHeight = 72.5.dp

/** Enthält alle Navigationsrouten der App als Konstanten. */
object Routes {
    const val HOME = "home"
    const val NOW_PLAYING = "now_playing"
    const val PLAYLIST_RATING = "playlist_rating"
    const val SONG_REQUEST = "song_request"
    const val MODERATOR_RATING = "moderator_rating"
    const val MODERATOR_PLAYLIST_SELECT = "moderator_playlist_select"
    const val MODERATOR_LIVE = "moderator_live"
    const val MODERATOR_LOGIN = "moderator_login"
    const val MODERATOR_MENU = "moderator_menu"
}

/**
 * Haupteinstiegspunkt der Anwendung.
 *
 * Instanziiert alle APIs, Services und das Activity-scoped [NowPlayingViewModel],
 * konfiguriert das globale Scaffold (TopBar mit Zurück-Navigation, MiniPlayer)
 * und richtet den NavHost mit einheitlichen Fade-Übergängen ein.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RadioFuldaTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route ?: Routes.HOME

                // ===== APIs (Sender-Systeme / Stubs) =====
                val nowPlayingApi = remember { StubNowPlayingApi() }
                val feedbackApi = remember { StubFeedbackApi() }
                val songRequestApi = remember { StubSongRequestApi() }
                val moderatorAlertApi = remember { StubModeratorAlertApi() }

                // ===== Services (UseCases) =====
                val playlistSelectionService = remember { PlaylistSelectionService() }
                val nowPlayingService = remember { NowPlayingService(nowPlayingApi, playlistSelectionService) }
                val playlistRatingService = remember { PlaylistRatingService(feedbackApi, moderatorAlertApi) }
                val songRequestService = remember { SongRequestService(songRequestApi, moderatorAlertApi) }
                val moderatorRatingService = remember { ModeratorRatingService(feedbackApi, moderatorAlertApi) }
                val moderatorService = remember { StubModeratorService() }
                val catalogService: RadioCatalogService = remember { StubRadioCatalogService() }

                // ===== Activity-scoped ViewModel (survives navigation) =====
                val nowPlayingViewModel: NowPlayingViewModel = viewModel(
                    factory = viewModelFactory { initializer { NowPlayingViewModel(nowPlayingService) } }
                )
                val nowPlayingState by nowPlayingViewModel.uiState.collectAsState()

                val title = when (currentRoute) {
                    Routes.NOW_PLAYING -> "Aktueller Titel"
                    Routes.PLAYLIST_RATING -> "Playlist bewerten"
                    Routes.SONG_REQUEST -> "Song wünschen"
                    Routes.MODERATOR_RATING -> "Moderator bewerten"
                    Routes.MODERATOR_LIVE -> "Live-Bewertungen"
                    Routes.MODERATOR_PLAYLIST_SELECT -> "Playlist auswählen"
                    Routes.MODERATOR_LOGIN -> "Moderator-Login"
                    Routes.MODERATOR_MENU -> "Moderator-Bereich"
                    else -> ""
                }

                Scaffold(
                    topBar = {
                        if (currentRoute == Routes.HOME) {
                            Spacer(modifier = Modifier.height(TopBarHeight))
                        } else {
                            SimpleTopBar(
                                title = title,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    },
                    bottomBar = {
                        if (nowPlayingState.track != null && currentRoute != Routes.NOW_PLAYING) {
                            MiniPlayer(
                                state = nowPlayingState,
                                onClick = { navController.navigate(Routes.NOW_PLAYING) }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME,
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { fadeIn(animationSpec = tween(300)) },
                        exitTransition = { fadeOut(animationSpec = tween(300)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                        popExitTransition = { fadeOut(animationSpec = tween(300)) }
                    ) {
                        composable(Routes.HOME) { HomeScreen(navController) }
                        composable(Routes.NOW_PLAYING) {
                            NowPlayingScreen(nowPlayingViewModel)
                        }
                        composable(Routes.PLAYLIST_RATING) {
                            PlaylistRatingScreen(playlistRatingService)
                        }
                        composable(Routes.SONG_REQUEST) {
                            SongRequestScreen(songRequestService)
                        }
                        composable(Routes.MODERATOR_RATING) {
                            ModeratorRatingScreen(moderatorRatingService, moderatorService)
                        }
                        composable(Routes.MODERATOR_PLAYLIST_SELECT) {
                            ModeratorPlaylistSelectScreen(
                                catalogService = catalogService,
                                selectionService = playlistSelectionService,
                                onDone = { navController.popBackStack() }
                            )
                        }
                        composable(Routes.MODERATOR_LIVE) {
                            ModeratorLiveScreen()
                        }
                        composable(Routes.MODERATOR_LOGIN) {
                            ModeratorLoginScreen(onSuccess = {
                                navController.navigate(Routes.MODERATOR_MENU) {
                                    popUpTo(Routes.MODERATOR_LOGIN) { inclusive = true }
                                }
                            })
                        }
                        composable(Routes.MODERATOR_MENU) {
                            ModeratorMenuScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Kompakter Titelstreifen am unteren Bildschirmrand.
 * Zeigt Cover, Titel, Interpret und einen Fortschrittsbalken;
 * beim Tippen navigiert der Nutzer zum vollständigen NowPlaying-Screen.
 */
@Composable
private fun MiniPlayer(state: NowPlayingUiState, onClick: () -> Unit) {
    val track = state.track ?: return
    val elapsed = (track.durationSec - state.remainingSec).coerceAtLeast(0)
    val progress = if (track.durationSec > 0) elapsed.toFloat() / track.durationSec else 0f

    Column {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = track.coverResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * Einfache TopBar mit Zurück-Button und Seitentitel.
 * Wird auf allen Screens außer dem Startbildschirm angezeigt.
 */
@Composable
private fun SimpleTopBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopBarHeight)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Zurück"
            )
        }
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

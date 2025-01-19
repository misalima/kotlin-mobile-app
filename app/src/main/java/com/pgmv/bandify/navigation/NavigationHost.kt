package com.pgmv.bandify.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.navigation.utils.getScreenTitle
import com.pgmv.bandify.ui.screen.AgendaScreen
import com.pgmv.bandify.ui.screen.ArquivosScreen
import com.pgmv.bandify.ui.screen.HomeScreen
import com.pgmv.bandify.ui.screen.NovaMusicaScreen
import com.pgmv.bandify.ui.screen.NovoEventoScreen
import com.pgmv.bandify.ui.screen.PerfilScreen
import com.pgmv.bandify.ui.screen.RepertorioScreen

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addScreen(
    route: String,
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    composable(
        route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        setScreenTitle(getScreenTitle(route))
        setHomeScreen(route == "home")
        setShowBottomBar(true)
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    dbHelper: DatabaseHelper,
    setShowBackButton: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {
        addScreen("home", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBackButton(false)
            HomeScreen(dbHelper, navController)
        }
        addScreen("agenda", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBackButton(false)
            AgendaScreen(dbHelper, navController)
        }
        addScreen("repertório", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBackButton(false)
            RepertorioScreen(dbHelper, navController)
        }
        addScreen("arquivos", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBackButton(false)
            ArquivosScreen(dbHelper)
        }
        addScreen("perfil", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBackButton(false)
            PerfilScreen(dbHelper)
        }
        addScreen("novo_evento", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBottomBar(false)
            setShowBackButton(true)
            NovoEventoScreen(dbHelper = dbHelper)
        }
        addScreen("nova_musica", setScreenTitle, setHomeScreen, setShowBottomBar) {
            setShowBottomBar(false)
            setShowBackButton(true)
            NovaMusicaScreen(dbHelper = dbHelper, navController = navController)
        }
    }
}
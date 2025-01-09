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
import com.pgmv.bandify.ui.screen.PerfilScreen
import com.pgmv.bandify.ui.screen.RepertorioScreen

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addScreen(
    route: String,
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
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
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    dbHelper: DatabaseHelper
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {
        addScreen("home", setScreenTitle, setHomeScreen) {
            HomeScreen(dbHelper)
        }
        addScreen("agenda", setScreenTitle, setHomeScreen) {
            AgendaScreen(dbHelper)
        }
        addScreen("repertório", setScreenTitle, setHomeScreen) {
            RepertorioScreen(dbHelper)
        }
        addScreen("arquivos", setScreenTitle, setHomeScreen) {
            ArquivosScreen(dbHelper)
        }
        addScreen("perfil", setScreenTitle, setHomeScreen) {
            PerfilScreen(dbHelper)
        }
    }
}
package com.pgmv.bandify.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.pgmv.bandify.ui.screen.AgendaScreen
import com.pgmv.bandify.ui.screen.ArquivosScreen
import com.pgmv.bandify.ui.screen.HomeScreen
import com.pgmv.bandify.ui.screen.PerfilScreen
import com.pgmv.bandify.ui.screen.RepertorioScreen

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addScreen(
    route: String,
    setIsHomeScreen: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    composable(
        route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        setIsHomeScreen(route == "home")
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    setScreenTitle: (String) -> Unit,
    setIsHomeScreen: (Boolean) -> Unit
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {
        addScreen("home", setIsHomeScreen) {
            HomeScreen(setScreenTitle)
        }
        addScreen("agenda", setIsHomeScreen) {
            AgendaScreen(setScreenTitle)
        }
        addScreen("repertório", setIsHomeScreen) {
            RepertorioScreen(setScreenTitle)
        }
        addScreen("arquivos", setIsHomeScreen) {
            ArquivosScreen(setScreenTitle)
        }
        addScreen("perfil", setIsHomeScreen) {
            PerfilScreen(setScreenTitle)
        }
    }
}
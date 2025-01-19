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
import com.pgmv.bandify.ui.screen.AuthenticationViewModel
import com.pgmv.bandify.ui.screen.HomeScreen
import com.pgmv.bandify.ui.screen.LoginScreen
import com.pgmv.bandify.ui.screen.NovoEventoScreen
import com.pgmv.bandify.ui.screen.PerfilScreen
import com.pgmv.bandify.ui.screen.RepertorioScreen

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addScreen(
    route: String,
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit = {},
    setShowBackButton: (Boolean) -> Unit = {},
    setShowTopBar: (Boolean) -> Unit = {},

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
    dbHelper: DatabaseHelper,
    setShowBackButton: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit,
    setShowTopBar: (Boolean) -> Unit,
    authenticationViewModel: AuthenticationViewModel
) {
    AnimatedNavHost(navController = navController, startDestination = "login") {
        composable(
            route = "login",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            setScreenTitle("Login")
            setHomeScreen(false)
            setShowBottomBar(false)
            setShowBackButton(false)
            setShowTopBar(false)
            LoginScreen( navController,authenticationViewModel)
            }
        addScreen("home", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            HomeScreen(dbHelper, navController)
        }
        addScreen("agenda", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            AgendaScreen(dbHelper, navController)
        }
        addScreen("repertório", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            RepertorioScreen(dbHelper)
        }
        addScreen("arquivos", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            ArquivosScreen(dbHelper)
        }
        addScreen("perfil", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            PerfilScreen(dbHelper)
        }
        addScreen("novo_evento", setScreenTitle, setHomeScreen, setShowBottomBar, setShowBackButton) {
            setShowBackButton(true)
            setShowBottomBar(false)
            setShowTopBar(true)
            NovoEventoScreen(dbHelper)
        }
    }
}

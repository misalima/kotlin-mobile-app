package com.pgmv.bandify.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
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
    arguments: List<NamedNavArgument> = emptyList(),
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit,
    content: @Composable (arguments: Map<String, String?>) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) { backStackEntry ->
        val argumentsMap = arguments.takeIf { it.isNotEmpty() }
            ?.let {
                backStackEntry.arguments?.keySet()?.associateWith { key ->
                    backStackEntry.arguments?.getString(key)
                }
            } ?: emptyMap()
        setScreenTitle(getScreenTitle(route))
        setHomeScreen(route == "home")
        setShowBottomBar(true)
        content(argumentsMap)
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
        addScreen(
            route = "home",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBackButton(false)
            HomeScreen(dbHelper, navController)
        }
        addScreen(
            route = "agenda",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBackButton(false)
            AgendaScreen(dbHelper, navController)
        }
        addScreen(
            route = "repertorio?event_id={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                    nullable = true
                }
            ),
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) { arguments ->
            val eventId = arguments["eventId"]
            setShowBackButton(false)
            RepertorioScreen(dbHelper, navController, eventId = eventId)
        }
        addScreen(
            route = "arquivos",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBackButton(false)
            ArquivosScreen(dbHelper)
        }
        addScreen(
            route = "perfil",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBackButton(false)
            PerfilScreen(dbHelper)
        }
        addScreen(
            route = "novo_evento",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBottomBar(false)
            setShowBackButton(true)
            NovoEventoScreen(dbHelper = dbHelper)
        }
        addScreen(
            route = "nova_musica",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar
        ) {
            setShowBottomBar(false)
            setShowBackButton(true)
            NovaMusicaScreen(dbHelper = dbHelper, navController = navController)
        }
    }
}
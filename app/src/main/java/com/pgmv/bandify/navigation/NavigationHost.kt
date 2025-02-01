package com.pgmv.bandify.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.ui.screen.*
import com.pgmv.bandify.utils.getScreenTitle
import com.pgmv.bandify.viewmodel.AuthenticationViewModel
import com.pgmv.bandify.viewmodel.UserViewModel


@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addScreen(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    setScreenTitle: (String) -> Unit,
    setHomeScreen: (Boolean) -> Unit,
    setShowBottomBar: (Boolean) -> Unit = {},
    setShowBackButton: (Boolean) -> Unit = {},
    setShowTopBar: (Boolean) -> Unit = {},
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
    setShowBottomBar: (Boolean) -> Unit,
    setShowTopBar: (Boolean) -> Unit,


    ) {
    val userViewModel: UserViewModel = viewModel()

    AnimatedNavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                authenticationViewModel = AuthenticationViewModel(dbHelper),
                userViewModel = userViewModel,
                navController = navController
            )
            setShowBottomBar(false)
            setShowTopBar(false)
        }
        addScreen(
            route = "register",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            setShowBottomBar(false)
            setShowTopBar(false)
            RegisterScreen(navController)
        }
        addScreen(
            route = "home",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            HomeScreen(dbHelper, navController)
        }
        addScreen(
            route = "agenda",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
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
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) { arguments ->
            val eventId = arguments["eventId"]
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            RepertorioScreen(dbHelper, navController, eventId = eventId)
        }
        addScreen(
            route = "arquivos",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            setShowBackButton(false)
            setShowBottomBar(true)
            setShowTopBar(true)
            ArquivosScreen(dbHelper)
        }
        addScreen(
            route = "perfil",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            ProfileScreen(dbHelper, userId = userViewModel.userId,navController)
        }
        addScreen(
            route = "novo_evento",
            setScreenTitle = setScreenTitle,
            setHomeScreen = setHomeScreen,
            setShowBottomBar = setShowBottomBar,
            setShowBackButton = setShowBackButton
        ) {
            setShowBackButton(true)
            setShowBottomBar(false)
            setShowTopBar(true)
            NovoEventoScreen(dbHelper)
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


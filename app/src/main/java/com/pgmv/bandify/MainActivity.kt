package com.pgmv.bandify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.navigation.NavigationHost
import com.pgmv.bandify.ui.components.BottomBar
import com.pgmv.bandify.ui.components.TopBar
import com.pgmv.bandify.ui.theme.BandifyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dbHelper = DatabaseHelper.getInstance(applicationContext)
            BandifyTheme {
                val navController = rememberAnimatedNavController()
                val (screenTitle, setScreenTitle) = remember { mutableStateOf("Home") }
                val (isHomeScreen, setIsHomeScreen) = remember { mutableStateOf(true) }

                Scaffold(
                    topBar = {
                        TopBar(screenTitle = screenTitle, isHomeScreen = isHomeScreen)
                    },
                    bottomBar = {
                        if (isHomeScreen) BottomBar(navController = navController)
                    },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavigationHost(navController, setScreenTitle, setIsHomeScreen, dbHelper)
                    }
                }
            }
        }
    }
}
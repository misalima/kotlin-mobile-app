package com.pgmv.bandify

import android.os.Bundle
import android.util.Log
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
import com.pgmv.bandify.domain.User
import com.pgmv.bandify.navigation.NavigationHost
import com.pgmv.bandify.ui.components.BottomBar
import com.pgmv.bandify.ui.components.TopBar
import com.pgmv.bandify.ui.screen.AuthenticationViewModel
import com.pgmv.bandify.ui.theme.BandifyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dbHelper = DatabaseHelper.getInstance(applicationContext)
        checkAndInsertUser(dbHelper)

        setContent {
            BandifyTheme {

                val navController = rememberAnimatedNavController()
                val (screenTitle, setScreenTitle) = remember { mutableStateOf("Home") }
                val (isHomeScreen, setIsHomeScreen) = remember { mutableStateOf(true) }
                val (showBackButton, setShowBackButton) = remember { mutableStateOf(false) }
                val (showBottomBar, setShowBottomBar) = remember { mutableStateOf(true) }
                val (showTopBar, setShowTopBar) = remember { mutableStateOf(true) }




                Scaffold(
                    topBar = {
                        if (showTopBar) TopBar(screenTitle = screenTitle, isHomeScreen = isHomeScreen, showBackButton = showBackButton, navController = navController)
                    },
                    bottomBar = {
                        if (showBottomBar) BottomBar(navController = navController)
                    },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        NavigationHost(
                            navController = navController,
                            setScreenTitle = setScreenTitle,
                            setHomeScreen = setIsHomeScreen,
                            dbHelper = dbHelper,
                            setShowBackButton = setShowBackButton,
                            setShowBottomBar = setShowBottomBar,
                            setShowTopBar = setShowTopBar,
                            authenticationViewModel = AuthenticationViewModel()
                        )
                    }
                }

            }
        }

    }
    private fun checkAndInsertUser(dbHelper: DatabaseHelper) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = dbHelper.userDao().getUserById(1)
            if (user == null) {

                val newUser = User(
                    id = 1, username = "johndoe",
                    firstName = "John",
                    surname = "Doe",
                    email = "johndoe@mail.com",
                    password = "123456",
                    phone = "12345678",
                    createdAt = LocalDate.now().toString()
                )
                val rowId = dbHelper.userDao().insertUser(newUser)
                withContext(Dispatchers.Main) {
                    if (rowId != -1L) {
                        Log.d("TestUser", "User inserted with ID: $rowId")
                    } else {
                        Log.d("TestUser", "Error inserting user")
                    }
                }
            }
        }
    }
}

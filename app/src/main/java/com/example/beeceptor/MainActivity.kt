package com.example.beeceptor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.beeceptor.Screens.DetailsScreen
import com.example.beeceptor.Screens.NetworkScreen
import com.example.beeceptor.ui.theme.BeeceptorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BeeceptorTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    /*Scaffold(topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Beeceptor")
                            },
                            navigationIcon = {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Home, contentDescription = "Back")
                                }
                            })

                    })*/


                    val navController = rememberNavController()
                    Scaffold(
                        topBar = {
                            val currentBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = currentBackStackEntry?.destination?.route
                            val screenName = currentRoute?.let {
                                if (it.contains("home")) {
                                    "Home"
                                } else {
                                    "Details"
                                }
                            }?:"Beeceptor"

                            TopAppBar(
                                title = { Text(screenName.toString()) },
                                navigationIcon = {
                                    if (currentRoute != "home") { // Show back arrow on screens other than "home"
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(Icons.Filled.ArrowBack, "backIcon")
                                        }
                                    } else {
                                        IconButton(onClick = { }) {
                                            Icon(Icons.Filled.Home, "Home")
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            // App()

                            NavHost(navController, startDestination = "home") {
                                composable(route = "home") {
                                    NetworkScreen(navController)
                                }
                                composable<UserItem> { backStackEntry ->
                                    val user: UserItem = backStackEntry.toRoute()
                                    DetailsScreen(user)
                                }

                            }
                        }
                    }

                }
            }

        }
    }
}
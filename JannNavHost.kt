package com.jann.ai.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jann.ai.core.ui.theme.JannColors
import com.jann.ai.feature.home.HomeScreen
import com.jann.ai.feature.placeholder.PlaceholderScreen

object Routes {
    const val HOME = "home"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
}

@Composable
fun JannNavHost() {
    val nav = rememberNavController()
    Box(Modifier.fillMaxSize().background(JannColors.Black)) {
        NavHost(nav, startDestination = Routes.HOME) {
            composable(Routes.HOME) {
                HomeScreen(
                    onOpenHistory = { nav.navigate(Routes.HISTORY) },
                    onOpenSettings = { nav.navigate(Routes.SETTINGS) },
                )
            }
            composable(Routes.HISTORY) { PlaceholderScreen("Conversation history", 2) { nav.popBackStack() } }
            composable(Routes.SETTINGS) { PlaceholderScreen("Settings", 7) { nav.popBackStack() } }
        }
    }
}

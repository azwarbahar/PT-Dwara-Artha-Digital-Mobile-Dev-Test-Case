package com.azwar.myticket.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.azwar.myticket.presentation.detail.DetailRoute
import com.azwar.myticket.presentation.home.HomeRoute
import com.azwar.myticket.presentation.navigation.NavRoutes.DETAIL
import com.azwar.myticket.presentation.navigation.NavRoutes.HOME
import com.azwar.myticket.presentation.navigation.NavRoutes.NEW_TICKET
import com.azwar.myticket.presentation.navigation.NavRoutes.SETTINGS
import com.azwar.myticket.presentation.newticket.NewTicketRoute
import com.azwar.myticket.presentation.settings.SettingsRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME) {
            HomeRoute(
                onTicketClick = { id -> navController.navigate(NavRoutes.detail(id)) },
                onAddTicketClick = { navController.navigate(NEW_TICKET) },
                onSettingsClick = { navController.navigate(SETTINGS) }
            )
        }
        composable(NEW_TICKET) {
            NewTicketRoute(
                onBack = { navController.popBackStack() },
                onTicketCreated = { navController.popBackStack() }
            )
        }
        composable(
            route = DETAIL,
            arguments = listOf(navArgument("ticketId") { type = LongType })
        ) {
            DetailRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(SETTINGS) {
            SettingsRoute(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
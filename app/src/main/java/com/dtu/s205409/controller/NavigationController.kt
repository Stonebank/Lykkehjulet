package com.dtu.s205409.controller

/**
 * author s205409 - Hassan Kassem
 *
 * The code is my own. It is used in Kind aswell.
 *
 * Reference link: https://github.com/Stonebank/kind/blob/master/app/src/main/java/com/dtu/kd3/kind/controller/NavigationController.kt
 *
 */

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dtu.s205409.views.ComposableView
import com.dtu.s205409.views.container.ShowFinishedView
import com.dtu.s205409.views.container.ShowGameView
import com.dtu.s205409.views.container.ShowInitialGameView
import com.dtu.s205409.views.container.ShowWelcomeView

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = ComposableView.WelcomeView.route) {
        composable(route = ComposableView.WelcomeView.route) {
            ShowWelcomeView(navController = navigationController)
        }
        composable(route = ComposableView.InitialGameView.route) {
            ShowInitialGameView(navController = navigationController)
        }
        composable(
            route = ComposableView.GameView.route + "/{name}", arguments = listOf(navArgument("name") {
                type = NavType.StringType
                nullable = true
            })) {
                entry -> ShowGameView(name = entry.arguments?.getString("name"), navController = navigationController)
        }
        composable(route = ComposableView.FinishedView.route + "/{name}/{hasWon}", arguments = listOf(navArgument("name") {
            type = NavType.StringType
            nullable = true
        }, navArgument("hasWon") {
            type = NavType.BoolType
        })) {
            entry ->
            entry.arguments?.getBoolean("hasWon")?.let { ShowFinishedView(name = entry.arguments?.getString("name"), hasWon = it, navController = navigationController) }
        }
    }
}
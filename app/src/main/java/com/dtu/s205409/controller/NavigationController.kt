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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dtu.s205409.model.GameViewModel
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.views.ComposableView
import com.dtu.s205409.views.container.ShowFinishedView
import com.dtu.s205409.views.container.ShowGameView
import com.dtu.s205409.views.container.ShowInitialGameView
import com.dtu.s205409.views.container.ShowWelcomeView

@Composable
fun Navigation(playerViewModel: PlayerViewModel, gameViewModel: GameViewModel) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = ComposableView.WelcomeView.route) {
        composable(route = ComposableView.WelcomeView.route) {
            ShowWelcomeView(navController = navigationController, playerViewModel = playerViewModel)
        }
        composable(route = ComposableView.InitialGameView.route) {
            ShowInitialGameView(navController = navigationController, playerViewModel = playerViewModel)
        }
        composable(route = ComposableView.GameView.route) {
            ShowGameView(navController = navigationController, playerViewModel = playerViewModel, gameViewModel = gameViewModel)
        }
        composable(route = ComposableView.FinishedView.route) {
            ShowFinishedView(navController = navigationController, playerViewModel = playerViewModel, gameViewModel = gameViewModel)
        }
    }
}
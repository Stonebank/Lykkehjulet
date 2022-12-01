package com.dtu.s205409.views

/**
 *
 * author s205409 Hassan K
 *
 * The code written below is my own. It is used in our main project.
 *
 * Reference: https://github.com/Stonebank/kind/blob/master/app/src/main/java/com/dtu/kd3/kind/views/ComposableView.kt
 *
 */

sealed class ComposableView(val route: String) {

    /** The welcome view will be shown when the user launches the application */
    object WelcomeView : ComposableView("welcome_view")

    /** The initial game view will be there to store the name of the user */
    object InitialGameView : ComposableView("initial_game_view")

    /** The game view */
    object GameView : ComposableView("game_view")

    /** Finish game view */
    object FinishedView : ComposableView("finish_view")

}
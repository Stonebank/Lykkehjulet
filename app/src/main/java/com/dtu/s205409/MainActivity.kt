package com.dtu.s205409

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import com.dtu.s205409.controller.Navigation
import com.dtu.s205409.model.GameViewModel
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.ui.theme.LykkehjuletTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerViewModel : PlayerViewModel by viewModels()
        val gameViewModel: GameViewModel by viewModels()
        setContent {
            LykkehjuletTheme {
                Surface {
                    Navigation(playerViewModel = playerViewModel, gameViewModel = gameViewModel)
                }
            }
        }
    }
}
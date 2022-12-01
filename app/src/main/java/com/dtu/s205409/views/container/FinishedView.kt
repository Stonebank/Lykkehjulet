package com.dtu.s205409.views.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dtu.s205409.model.GameViewModel
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor

@Composable
fun ShowFinishedView(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(color = primaryBackground), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (gameViewModel.hasWon(gameViewModel.displayRandomWord())) "Tillykke ${playerViewModel.name.value}, du har vundet! Ønsker du at spille igen?" else "Øv ${playerViewModel.name.value}, du mistede alle dine liv", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text("Endelig score: ${playerViewModel.points.value} points", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    gameViewModel.reset(playerViewModel = playerViewModel)
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(primaryButtonColor)) {
                Text("Spil igen")
            }
        }
    }
}
package com.dtu.s205409.views.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dtu.s205409.R
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import com.dtu.s205409.views.ComposableView

@Composable
fun ShowWelcomeView(navController: NavController, playerViewModel: PlayerViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryBackground),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Velkommen til ${stringResource(id = R.string.app_name)}!", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
            Text(text = "Føler du dig heldig? Sæt gang i lykkehjulet nu! \uD83C\uDF40", color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = { navController.navigate(route = ComposableView.InitialGameView.route) }, colors = ButtonDefaults.buttonColors(
                primaryButtonColor), shape = CircleShape) {
                Text("Lad os komme igang", color = Color.White)
            }
        }
    }
}

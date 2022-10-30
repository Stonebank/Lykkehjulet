package com.dtu.s205409.views.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.welcomeButtonColor

@Composable
fun ShowWelcomeView(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = primaryBackground), verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Velkommen til ${stringResource(id = R.string.app_name)}", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center)
            Text(text = "Siden dette er din første gang vil vi gennemgå reglerne for lykkehjulet", color = Color.White, textAlign = TextAlign.Center, fontSize = 12.sp)
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(welcomeButtonColor), shape = CircleShape) {
                Text("Lad os komme igang", color = Color.White)
            }
        }
    }
}

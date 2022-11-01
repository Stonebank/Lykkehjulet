package com.dtu.s205409.views.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dtu.s205409.R
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import com.dtu.s205409.views.ComposableView

@Composable
fun ShowWelcomeView(navController: NavController) {
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


@Preview
@Composable
fun PreviewWelcomeView() {
    ShowWelcomeView(rememberNavController())
}
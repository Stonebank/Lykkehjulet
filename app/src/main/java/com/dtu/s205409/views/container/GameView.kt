package com.dtu.s205409.views.container

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.commandiron.spin_wheel_compose.DefaultSpinWheel
import com.commandiron.spin_wheel_compose.SpinWheelDefaults
import com.dtu.s205409.R
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import java.util.*


@Composable
fun ShowGameView(name: String?, navController: NavController) {

    val pointList by remember {
        mutableStateOf(listOf("Fallit", "100", "300", "500", "600", "800", "1000", "1500"))
    }

    var points by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(5) }

    var isSpinning by remember { mutableStateOf(false) }
    var resultDegree by remember { mutableStateOf(0f) }

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(color = primaryBackground)) {
            Column(modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .shadow(1.dp)
                .background(
                    Color.White
                )
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(100.dp)) {
                Text(text = name?.let { capitalize(it) } ?: "Uangivet navn", color = Color.Black, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Text("$points points", fontWeight = FontWeight.SemiBold)
                Text("+500 points hvis du gætter rigtig!", textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.End) {
                    for (heart in 0 until lives) {
                        Image(painterResource(id = R.drawable.heart), "heart", modifier = Modifier.size(28.dp, 24.dp))
                    }
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(30.dp))
            DefaultSpinWheel(
                isSpinning = isSpinning,
                onFinish = {
                           isSpinning = false
                },
                animationAttr = SpinWheelDefaults.spinWheelAnimationAttr(
                    pieCount = pointList.size,
                    durationMillis = 4000,
                    delayMillis = 200,
                    rotationPerSecond = 2f,
                    easing = FastOutSlowInEasing,
                    startDegree = 90f
                ),
                colors = SpinWheelDefaults.spinWheelColors(frameColor = Color.Black, selectorColor = Color.Red),
                dimensions = SpinWheelDefaults.spinWheelDimensions(300.dp), resultDegree = resultDegree) {
                    pieIndex -> Text(text = pointList[pieIndex])
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    if (isSpinning) {
                        Toast.makeText(context, "Du har allerede drejet hjulet", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    resultDegree = (0..360).random().toFloat()
                    isSpinning = true
                }, colors = ButtonDefaults.buttonColors(if (isSpinning) Color.White.copy(alpha = 0.5f) else primaryButtonColor)) {
                Text("Drej hjulet", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

}

@Preview
@Composable
fun Preview() {
    ShowGameView(name = "Hassan", navController = rememberNavController())
}


fun capitalize(input: String) : String {
    return input.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

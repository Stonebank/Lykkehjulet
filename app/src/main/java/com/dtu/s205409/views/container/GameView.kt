package com.dtu.s205409.views.container

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.spin_wheel_compose.DefaultSpinWheel
import com.commandiron.spin_wheel_compose.SpinWheelDefaults
import com.dtu.s205409.R
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.welcomeButtonColor

@Composable
fun ShowGameView(name: String?) {

    val pointList by remember {
        mutableStateOf(listOf("Fallit", "100", "300", "500", "600", "800", "1000", "1500"))
    }

    var result by remember { mutableStateOf("0") }

    var isSpinning by rememberSaveable { mutableStateOf(false) }

    var points by rememberSaveable { mutableStateOf(0) }
    var lives by rememberSaveable { mutableStateOf(5) }

    var resultDegree by rememberSaveable { mutableStateOf(0f) }
    var index by rememberSaveable { mutableStateOf(0) }

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(primaryBackground)) {
            Text(text = name ?: "Fejl", fontSize = 48.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp, 5.dp))
            Text(text = "$points points", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(5.dp))
        }
        Row(modifier = Modifier.padding(bottom = 25.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
            for (heart in 1..lives) {
                Image(painterResource(id = R.drawable.heart), "heart", modifier = Modifier.size(24.dp, 24.dp))
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
            DefaultSpinWheel(
                isSpinning = isSpinning,
                onFinish = {
                            isSpinning = false
                            result = pointList[index]
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
                    index -> Text(text = pointList[index])
            }
            Text(text = result, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Button(
                onClick = {
                          if (isSpinning) {
                              Toast.makeText(context, "Du har allerede drejet hjulet", Toast.LENGTH_SHORT).show()
                              return@Button
                          }
                    result = "Drejer hjulet.."
                    index = pointList.indices.random()
                    resultDegree = (0..360).random().toFloat()
                    isSpinning = true
                },
                colors = ButtonDefaults.buttonColors(if (isSpinning) Color.White.copy(alpha = 0.5f) else welcomeButtonColor)) {
                Text("Drej hjulet", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }

}

@Preview
@Composable
fun Preview() {
    ShowGameView(name = "Hassan")
}

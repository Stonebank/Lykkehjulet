package com.dtu.s205409.views.container

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dtu.s205409.R
import com.dtu.s205409.model.GameViewModel
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import com.dtu.s205409.utility.GameWord
import com.dtu.s205409.views.ComposableView

@Composable
fun ShowGameView(navController: NavController, playerViewModel: PlayerViewModel, gameViewModel: GameViewModel) {

    var error by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(color = primaryBackground)) {
            Column(modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .shadow(5.dp)
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(120.dp)) {
                Text(text = playerViewModel.name.value, color = Color.Black, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Text("${playerViewModel.points.value} points ${if (gameViewModel.randomWord.value == GameWord.PLACEHOLDER) "" else "| Categorien er ${gameViewModel.randomWord.value.getCategory()}"}", fontWeight = FontWeight.SemiBold)
                Text(text = if (gameViewModel.pointListResult.value.isNotEmpty()) gameViewModel.resultMessage() else "", textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.End) {
                    for (heart in 0 until playerViewModel.lives.value) {
                        Image(painterResource(id = R.drawable.heart), "heart", modifier = Modifier.size(24.dp, 24.dp))
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.padding(30.dp))
                if (gameViewModel.hasSpinned.value) {
                    if (gameViewModel.isFallit()) {
                        gameViewModel.toggleSpinned()
                        playerViewModel.resetPoints()
                        Toast.makeText(
                            context,
                            "Øv, du landede på fallit og mistede alle dine points",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Text(
                        text = gameViewModel.displayRandomWord(),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = input,
                        onValueChange = {
                            if ((it.isNotEmpty() && it.toCharArray()[0].isDigit()) || !it.matches("[a-zA-Z ]*".toRegex())) {
                                error = true
                                return@OutlinedTextField
                            }
                            if (it.length > 1 && !gameViewModel.guessingWord.value) {
                                error = true
                                return@OutlinedTextField
                            }
                            input = it
                            error = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (error) Icons.Default.Warning else Icons.Default.Edit,
                                contentDescription = "",
                                tint = if (error) MaterialTheme.colors.error else MaterialTheme.colors.background
                            )
                        },
                        label = { Text(text = "Gæt", color = Color.White) },
                        placeholder = {
                            Text(
                                "Indtast dit gæt",
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(Color.White),
                        singleLine = true,
                        isError = error,
                        keyboardActions = KeyboardActions { gameViewModel.checkInput() }
                    )
                    if (error) {
                        Text(
                            text = gameViewModel.getInputErrorMessage(),
                            color = MaterialTheme.colors.error
                        )
                    }
                } else {
                    Text(
                        text = if (gameViewModel.pointListResult.value.isNotEmpty() && !gameViewModel.isFallit()) "+${gameViewModel.pointListResult.value}" else "Drej hjulet",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        if (gameViewModel.hasSpinned.value) {
                            gameViewModel.setInput(input)
                            if (gameViewModel.checkInput()) {
                                error = true
                                return@Button
                            }
                            if (gameViewModel.isCorrectGuess()) {
                                playerViewModel.addPoints(gameViewModel.pointListResult.value.toInt())
                                Toast.makeText(
                                    context,
                                    "Du gættede rigtigt og modtog ${gameViewModel.pointListResult.value} points!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                playerViewModel.removeLifePoint()
                                if (gameViewModel.guessingWord.value)
                                    playerViewModel.removeAllLives()
                                if (playerViewModel.lives.value == 0) {
                                    navController.navigate(route = ComposableView.FinishedView.route)
                                    return@Button
                                }
                                Toast.makeText(
                                    context,
                                    "Du gættede forkert og mistede et liv!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            gameViewModel.guessedInputs.value.add(gameViewModel.input.value)
                            if (gameViewModel.hasBoughtVocal.value)
                                gameViewModel.toggleBoughtVocal()
                            if (gameViewModel.guessingWord.value)
                                gameViewModel.toggleGuessingWord()
                            error = false
                            if (gameViewModel.hasWon(gameViewModel.displayRandomWord())) {
                                navController.navigate(route = ComposableView.FinishedView.route)
                                return@Button
                            }
                            input = ""
                            gameViewModel.resetSpin()
                            return@Button
                        }
                        if (gameViewModel.randomWord.value == GameWord.PLACEHOLDER)
                            gameViewModel.setRandomWord(gameViewModel.generateRandomWord())
                        gameViewModel.generateRandomPointResult()
                        gameViewModel.toggleSpinned()
                    }, colors = ButtonDefaults.buttonColors(primaryButtonColor)
                ) {
                    Text(
                        text = if (gameViewModel.hasSpinned.value) "Gæt" else "Drej hjulet",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (gameViewModel.hasSpinned.value) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)) {
                        Button(onClick = {
                            gameViewModel.buyVocal(playerViewModel = playerViewModel, localContext = context)
                        }, colors = ButtonDefaults.buttonColors(primaryButtonColor), shape = CircleShape) {
                            Text("Køb en vokal", color = Color.White)
                        }
                        Button(onClick = {
                             gameViewModel.guessWord(localContext = context)
                        }, colors = ButtonDefaults.buttonColors(primaryButtonColor), shape = CircleShape) {
                            Text("Gæt ordet", color = Color.White)
                        }
                    }
                }
            }
        }
    }

}
package com.dtu.s205409.views.container

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dtu.s205409.R
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import com.dtu.s205409.utility.GameWord
import com.dtu.s205409.views.ComposableView
import java.util.*


@Composable
fun ShowGameView(name: String?, navController: NavController) {

    val pointList by remember {
        mutableStateOf(listOf("Fallit", "100", "300", "500", "600", "800", "1000", "1500"))
    }

    var points by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(5) }

    var hasSpinned by remember { mutableStateOf(false) }
    var pointListResult by remember { mutableStateOf("") }

    var randomWord by remember { mutableStateOf(GameWord.PLACEHOLDER) }
    val guessedInputs by remember { mutableStateOf(ArrayList<String>()) }
    var input by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun checkInput() : Boolean {
        return input.isEmpty() || input.length > 1 || input in guessedInputs
    }

    fun resetGame() {
        error = false
        points = 0
        lives = 5
        hasSpinned = false
        pointListResult = ""
        randomWord = GameWord.PLACEHOLDER
        guessedInputs.clear()
        input = ""
    }

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
                .height(120.dp)) {
                Text(text = name?.let { capitalize(it) } ?: "Uangivet navn", color = Color.Black, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Text("$points points ${if (randomWord == GameWord.PLACEHOLDER) "" else " |  Categorien er ${capitalize(randomWord.getCategory().lowercase())}"}", fontWeight = FontWeight.SemiBold)
                Text(if (pointListResult.isNotEmpty()) resultMessage(pointListResult) else "", textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.End) {
                    for (heart in 0 until lives) {
                        Image(painterResource(id = R.drawable.heart), "heart", modifier = Modifier.size(24.dp, 24.dp))
                    }
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(30.dp))
            if (hasSpinned) {
                if (isFallit(pointListResult)) {
                    hasSpinned = false
                    points = 0
                    Toast.makeText(context, "Øv, du landede på fallit og mistede alle dine points!", Toast.LENGTH_LONG).show()
                }
                Text(text = capitalize(displayRandomWord(guessedInputs, randomWord)), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it
                        error = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (error) Icons.Default.Warning else Icons.Default.Edit,
                            contentDescription = "error",
                            tint = if (error) MaterialTheme.colors.error else MaterialTheme.colors.background
                        )
                    },
                    label = { Text("Gæt", color = Color.White) },
                    placeholder = { Text("Indtast dit gæt", color = Color.White.copy(alpha = 0.5f)) },
                    colors = TextFieldDefaults.textFieldColors(Color.White),
                    singleLine = true,
                    isError = error,
                    keyboardActions = KeyboardActions { checkInput() }
                )
                if (error) {
                    Text(inputErrorMessage(input, guessedInputs), color = MaterialTheme.colors.error)
                }
            } else {
                Text(text = if (pointListResult.isNotEmpty() && !isFallit(pointListResult)) "$pointListResult points" else "Drej hjulet", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    if (hasSpinned) {
                        if (checkInput()) {
                            error = true
                            return@Button
                        }
                        if (isCorrectGuess(input, randomWord)) {
                            Toast.makeText(context, "Du gættede rigtigt og modtog $pointListResult points!", Toast.LENGTH_LONG).show()
                            points += pointListResult.toInt()
                        } else {
                            Toast.makeText(context, "Du gættede forkert og mistede et liv!", Toast.LENGTH_LONG).show()
                            lives--
                            if (lives == 0) {
                                navController.navigate(route = ComposableView.FinishedView.passArguments( "$name", "false"))
                                Toast.makeText(context, "Du har tabt!", Toast.LENGTH_LONG).show()
                                resetGame()
                                return@Button
                            }
                        }
                        guessedInputs.add(input)
                        if (hasWon(displayRandomWord(guessedInputs, randomWord))) {
                            Toast.makeText(context, "Tillykke, du gættede ordet!", Toast.LENGTH_LONG).show()
                            navController.navigate(route = ComposableView.FinishedView.passArguments( "$name", "true"))
                            resetGame()
                            return@Button
                        }
                        input = ""
                        pointListResult = ""
                        hasSpinned = false
                        return@Button
                    }
                    if (randomWord == GameWord.PLACEHOLDER)
                        randomWord = generateRandomWord()
                    pointListResult = pointList[Random().nextInt(pointList.size)]
                    hasSpinned = true
                }, colors = ButtonDefaults.buttonColors(if (hasSpinned) Color.Green else primaryButtonColor)) {
                Text(if (hasSpinned) "Gæt" else "Drej hjulet", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }

}

@Preview
@Composable
fun Preview() {
    ShowGameView(name = "Hassan", navController = rememberNavController())
}

fun hasWon(word: String) : Boolean {
    for (element in word) {
        if (element == '*') {
            return false
        }
    }
    return true
}

fun generateRandomWord(): GameWord {
    val random = Random()
    val randomIndex = random.nextInt(GameWord.values().size)
    while (GameWord.values()[randomIndex] == GameWord.PLACEHOLDER) {
        return generateRandomWord()
    }
    return GameWord.values()[randomIndex]
}

fun isCorrectGuess(guess: String, word: GameWord): Boolean {
    for (element in guess) {
        for (j in 0 until word.word.length) {
            if (element.equals(word.word[j], ignoreCase = true)) {
                return true
            }
        }
    }
    return false
}

fun displayRandomWord(guessedInputs: ArrayList<String>, word: GameWord): String {
    val display = StringBuilder()
    for (i in 0 until word.word.length) {
        if (word.word[i].toString().contains("-")) {
            display.append("-")
            continue
        }
        if (word.word[i].toString().contains(" ")) {
            display.append(" ")
            continue
        }
        if (guessedInputs.contains(word.word[i].toString()))
            display.append(word.word[i])
        else
            display.append("*")
    }
    return display.toString()
}

fun inputErrorMessage(input: String, guessedInputs: List<String>): String {
    if (input in guessedInputs)
        return "Du har allerede gættet på dette bogstav!"
    if (input.length > 1 || input.isEmpty())
        return "Dit gæt kan kun være et bogstav!"
    if (input.toCharArray()[0].isDigit())
        return "Dit gæt kan ikke være et tal!"
    return "Du skal indtaste et bogstav!"
}

fun resultMessage(result: String) : String {
    if (isFallit(result))
        return "Øv! Du ramte fallit"
    return "+$result points hvis du gætter rigtig!"
}

fun isFallit(result: String) : Boolean {
    return result.equals("fallit", ignoreCase = true)
}

fun capitalize(input: String) : String {
    return input.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

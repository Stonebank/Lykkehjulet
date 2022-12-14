package com.dtu.s205409.views.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dtu.s205409.model.PlayerViewModel
import com.dtu.s205409.ui.theme.primaryBackground
import com.dtu.s205409.ui.theme.primaryButtonColor
import com.dtu.s205409.views.ComposableView

@Composable
fun ShowInitialGameView(navController: NavController, playerViewModel: PlayerViewModel) {

    println(playerViewModel.name.value)

    var name by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }

    fun checkName() : Boolean {
        return name.isEmpty()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = primaryBackground),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hvad kan vi kalde dig? \uD83D\uDE04",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    error = false
                },
                leadingIcon = {
                    Icon(
                        imageVector = if (error) Icons.Default.Warning else Icons.Default.Person,
                        contentDescription = "error",
                        tint = if (error) colors.error else colors.background
                    )
                },
                label = { Text("Navn", color = Color.White) },
                placeholder = { Text("Dit navn", color = Color.White.copy(alpha = 0.5f)) },
                colors = TextFieldDefaults.textFieldColors(Color.White),
                singleLine = true,
                isError = error,
                keyboardActions = KeyboardActions { checkName() }
            )
            if (error) {
                Text(
                    if (name.length > 10) "Hov... dit angivet navn er m??ske lidt for langt" else "Hov... Intet navn angivet",
                    color = colors.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    if (name.isEmpty()) {
                        error = true
                        return@Button
                    }
                    if (name.length > 10) {
                        error = true
                        return@Button
                    }
                    playerViewModel.setName(name)
                    navController.navigate(route = ComposableView.GameView.route)
                },
                colors = ButtonDefaults.buttonColors(primaryButtonColor), shape = CircleShape
            ) {
                Text("Klar!", color = Color.White)
            }
        }
    }
}

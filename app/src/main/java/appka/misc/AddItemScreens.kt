package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewsItemScreen(onAdd: (NewsItem) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newNewsItem = NewsItem(title = title, description = description, date = date, likes = 0)
                    onAdd(newNewsItem)
                } else {
                    // Show error
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Add News Item", color = Color.Black)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHubItemScreen(onAdd: (HubsItem) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newHubItem = HubsItem(title = title, description = description)
                    onAdd(newHubItem)
                } else {
                    // Show error
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Add Hub Item", color = Color.Black)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStatsItemScreen(onAdd: (StatsItem) -> Unit) {
    var gameName by remember { mutableStateOf("") }
    var wins by remember { mutableStateOf("") }
    var losses by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = gameName,
            onValueChange = { gameName = it },
            label = { Text("Game Name", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = wins,
            onValueChange = { wins = it },
            label = { Text("Wins", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = losses,
            onValueChange = { losses = it },
            label = { Text("Losses", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (gameName.isNotEmpty() && wins.isNotEmpty() && losses.isNotEmpty()) {
                    val newStatsItem = StatsItem(
                        gameName = gameName,
                        wins = wins.toInt(),
                        losses = losses.toInt()
                    )
                    onAdd(newStatsItem)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Add Stats Item", color = Color.Black)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameItemScreen(onAdd: (Game) -> Unit) {
    var gameName by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = gameName,
            onValueChange = { gameName = it },
            label = { Text("Game Name", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (gameName.isNotEmpty() && desc.isNotEmpty()) {
                    val newGameItem = Game(
                        name = gameName,
                        desc = desc
                    )
                    onAdd(newGameItem)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Add Game Item", color = Color.Black)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGuideItemScreen(onAdd: (Guide) -> Unit) {
    var title by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Link", color = colorResource(id = R.color.light_blue)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.light_blue),
                unfocusedTextColor = colorResource(id = R.color.light_blue),
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = colorResource(id = R.color.light_blue)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (title.isNotEmpty() && link.isNotEmpty()) {
                    val newGuideItem = Guide(
                        title = title,
                        link = link
                    )
                    onAdd(newGuideItem)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Add Guide Item", color = Color.Black)
        }
    }
}


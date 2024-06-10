package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.ui.theme.TowerDefenseSeminarTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import kotlinx.coroutines.launch
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.R
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.foundation.clickable
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Game
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Guide
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.NewsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.StatsItem

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.post {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        setContent {
            TowerDefenseSeminarTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent()
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBarContent(navController, onMenuClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            })
                        },
                        bottomBar = { BottomNavigationBar(navController) },
                        containerColor = colorResource(id = R.color.dark_gray)
                    ) { innerPadding ->
                        NavHost(navController, startDestination = "news", Modifier.padding(innerPadding)) {
                            composable("news") { NewsScreen(listOf(), false, {}) }
                            composable("hub") { HubScreen(listOf(), {}, {}, {}) }
                            composable("stats") { StatsScreen(listOf(), {}, {}, {}) }
                            composable("games") { GamesScreen(listOf(), {}, {}) }
                            composable("guides") { GuidesScreen(listOf(), {}) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Registered Games", style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(16.dp))
        for (i in 1..10) {
            Text("Game $i", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(navController: NavHostController, onMenuClicked: () -> Unit) {
    val context = LocalContext.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""

    TopAppBar(
        title = { Text(getCurrentTitle(currentDestination), style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue),
            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = colorResource(id = R.color.light_blue))
            }
        },
        actions = {
            IconButton(onClick = {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            }) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = colorResource(id = R.color.light_blue))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.Black) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.NewReleases, contentDescription = null, tint = colorResource(id = R.color.light_blue)) },
            label = { Text("NEWS", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            selected = navController.currentDestination?.route == "news",
            onClick = { navController.navigate("news") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Hub, contentDescription = null, tint = colorResource(id = R.color.light_blue)) },
            label = { Text("HUB", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            selected = navController.currentDestination?.route == "hub",
            onClick = { navController.navigate("hub") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null, tint = colorResource(id = R.color.light_blue)) },
            label = { Text("STATS", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            selected = navController.currentDestination?.route == "stats",
            onClick = { navController.navigate("stats") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Games, contentDescription = null, tint = colorResource(id = R.color.light_blue)) },
            label = { Text("GAMES", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            selected = navController.currentDestination?.route == "games",
            onClick = { navController.navigate("games") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = colorResource(id = R.color.light_blue)) },
            label = { Text("GUIDES", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            selected = navController.currentDestination?.route == "guides",
            onClick = { navController.navigate("guides") }
        )
    }
}

@Composable
fun getCurrentTitle(route: String): String {
    return when (route) {
        "news" -> "NEWS"
        "hub" -> "HUB"
        "stats" -> "STATS"
        "games" -> "GAMES"
        "guides" -> "GUIDES"
        else -> ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(newsItems: List<NewsItem>, isAdmin: Boolean, onAddNewsItem: (NewsItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isAdmin) {
            // UI for adding a new news item
            var title by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    cursorColor = colorResource(id = R.color.light_blue),
                    focusedLabelColor = colorResource(id = R.color.light_blue),
                    unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    focusedIndicatorColor = colorResource(id = R.color.light_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                    focusedTextColor = colorResource(id = R.color.light_blue),
                    unfocusedTextColor = colorResource(id = R.color.light_blue)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    cursorColor = colorResource(id = R.color.light_blue),
                    focusedLabelColor = colorResource(id = R.color.light_blue),
                    unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    focusedIndicatorColor = colorResource(id = R.color.light_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                    focusedTextColor = colorResource(id = R.color.light_blue),
                    unfocusedTextColor = colorResource(id = R.color.light_blue)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNewsItem(NewsItem(title = title, description = description))
                }
            }) {
                Text("Add News Item", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        newsItems.forEach { newsItem ->
            Text(text = newsItem.title, style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsItem.description, style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HubScreen(newsItems: List<NewsItem>, onRefresh: () -> Unit, onShare: (NewsItem) -> Unit, onLike: (NewsItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onRefresh) {
            Text("Refresh", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        newsItems.forEach { item ->
            Text(text = item.title, style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue))
            Row {
                IconButton(onClick = { onLike(item) }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Like", tint = colorResource(id = R.color.light_blue))
                }
                IconButton(onClick = { onShare(item) }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = colorResource(id = R.color.light_blue))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatsScreen(statsItems: List<StatsItem>, onAddStat: () -> Unit, onEditStat: (StatsItem) -> Unit, onDeleteStat: (StatsItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        statsItems.forEach { item ->
            Text(text = item.gameName, style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Wins: ${item.wins} Losses: ${item.losses}", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue))
            Row {
                IconButton(onClick = { onEditStat(item) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = colorResource(id = R.color.light_blue))
                }
                IconButton(onClick = { onDeleteStat(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = colorResource(id = R.color.light_blue))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onAddStat) {
            Text("Add Statistic", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun GamesScreen(games: List<Game>, onAddGame: () -> Unit, onGameClick: (Game) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        games.forEach { game ->
            Text(text = game.name, style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue), modifier = Modifier.clickable { onGameClick(game) })
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onAddGame) {
            Text("Add Game", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun GuidesScreen(guides: List<Guide>, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onRefresh) {
            Text("Refresh", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        guides.forEach { guide ->
            Text(text = guide.title, style = MaterialTheme.typography.titleLarge, color = colorResource(id = R.color.light_blue))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = guide.link, style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue), modifier = Modifier.clickable {
                // Handle guide triedu
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

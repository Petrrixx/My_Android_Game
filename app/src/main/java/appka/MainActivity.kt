package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.room.Room
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Game
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Guide
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.HubsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.NewsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.StatsItem



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)

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

                var isAdmin by remember { mutableStateOf(false) }

                // Retrieve isAdmin value from intent
                val isAdminIntent = intent.getBooleanExtra("isAdmin", false)

                LaunchedEffect(Unit) {
                    isAdmin = isAdminIntent
                }

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
                        containerColor = Color.Black
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .border(2.dp, colorResource(id = R.color.light_blue), RoundedCornerShape(4.dp))
                                .background(colorResource(id = R.color.dark_gray))
                        ) {
                            NavHost(navController, startDestination = "news") {
                                composable("news") {
                                    NewsScreen(listOf(), isAdmin, {})
                                }
                                composable("hub") {
                                    HubScreen(listOf(), isAdmin, {}, {})
                                }
                                composable("stats") {
                                    StatsScreen(listOf(), isAdmin, {}, {})
                                }
                                composable("games") {
                                    GamesScreen(listOf(), isAdmin, {}, {})
                                }
                                composable("guides") {
                                    GuidesScreen(listOf(), isAdmin, {})
                                }
                            }
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.Black) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.NewReleases, contentDescription = null, tint = if (currentRoute == "news") colorResource(id = R.color.light_blue) else Color.White) },
            label = { Text("NEWS", color = if (currentRoute == "news") colorResource(id = R.color.light_blue) else Color.White) },
            selected = currentRoute == "news",
            onClick = { navController.navigate("news") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Hub, contentDescription = null, tint = if (currentRoute == "hub") colorResource(id = R.color.light_blue) else Color.White) },
            label = { Text("HUB", color = if (currentRoute == "hub") colorResource(id = R.color.light_blue) else Color.White) },
            selected = currentRoute == "hub",
            onClick = { navController.navigate("hub") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null, tint = if (currentRoute == "stats") colorResource(id = R.color.light_blue) else Color.White) },
            label = { Text("STATS", color = if (currentRoute == "stats") colorResource(id = R.color.light_blue) else Color.White) },
            selected = currentRoute == "stats",
            onClick = { navController.navigate("stats") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Games, contentDescription = null, tint = if (currentRoute == "games") colorResource(id = R.color.light_blue) else Color.White) },
            label = { Text("GAMES", color = if (currentRoute == "games") colorResource(id = R.color.light_blue) else Color.White) },
            selected = currentRoute == "games",
            onClick = { navController.navigate("games") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = if (currentRoute == "guides") colorResource(id = R.color.light_blue) else Color.White) },
            label = { Text("GUIDES", color = if (currentRoute == "guides") colorResource(id = R.color.light_blue) else Color.White) },
            selected = currentRoute == "guides",
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

@Composable
fun NewsScreen(newsItems: List<NewsItem>, isAdmin: Boolean, onAddNewsItem: (NewsItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isAdmin) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { /* Pridávanie? */ }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add News Item",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
                IconButton(onClick = { /* Refresh čoho? */ }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh News Items",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }

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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            onAddNewsItem(NewsItem(title = title, description = description))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add News Item", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            newsItems.forEach { newsItem ->
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = newsItem.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
    @Composable
    fun HubScreen(
        hubItems: List<HubsItem>,
        isAdmin: Boolean,
        onAddHubItem: (HubsItem) -> Unit,
        onRefreshHubItems: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isAdmin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Pridávanie? */ }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Hub Item",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                    IconButton(onClick = { onRefreshHubItems() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh Hub Items",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                }
                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }

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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            onAddHubItem(HubsItem(title = title, description = description))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Hub Item", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            hubItems.forEach { hubItem ->
                Text(
                    text = hubItem.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = hubItem.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun StatsScreen(
        statsItems: List<StatsItem>,
        isAdmin: Boolean,
        onAddStatsItem: (StatsItem) -> Unit,
        onRefreshStatsItems: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isAdmin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Pridávanie? */ }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Stats Item",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                    IconButton(onClick = { onRefreshStatsItems() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh Stats Items",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                }
                var title by remember { mutableStateOf("") }

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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.isNotEmpty()) {
                            onAddStatsItem(StatsItem(gameName = title, wins = 0, losses = 0))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Stats Item", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            statsItems.forEach { statsItem ->
                Text(
                    text = statsItem.gameName,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }

    @Composable
    fun GamesScreen(
        gamesItems: List<Game>,
        isAdmin: Boolean,
        onAddGamesItem: (Game) -> Unit,
        onRefreshGamesItems: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isAdmin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Pridávanie? */ }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Games Item",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                    IconButton(onClick = { onRefreshGamesItems() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh Games Items",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                }
                var name by remember { mutableStateOf("") }
                var desc by remember { mutableStateOf("") }

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Title", color = colorResource(id = R.color.light_blue)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(id = R.color.light_blue),
                        unfocusedTextColor = colorResource(id = R.color.light_blue),
                        focusedContainerColor = Color.DarkGray,
                        unfocusedContainerColor = Color.DarkGray,
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (name.isNotEmpty() && desc.isNotEmpty()) {
                            onAddGamesItem(Game(name = name, desc = desc))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Games Item", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            gamesItems.forEach { game ->
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun GuidesScreen(guidesItems: List<Guide>, isAdmin: Boolean, onAddGuidesItem: (Guide) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isAdmin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Pridávanie? */ }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Guides Item",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                    IconButton(onClick = { /* Refresh? */ }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh Guides Items",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                }
                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }

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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
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
                        disabledContainerColor = Color.DarkGray,
                        cursorColor = colorResource(id = R.color.light_blue),
                        focusedIndicatorColor = colorResource(id = R.color.light_blue),
                        unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                        focusedLabelColor = colorResource(id = R.color.light_blue),
                        unfocusedLabelColor = colorResource(id = R.color.light_blue),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            onAddGuidesItem(Guide(title = title, link = description))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Guides Item", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            guidesItems.forEach { guidesItem ->
                Text(
                    text = guidesItem.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = guidesItem.link,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
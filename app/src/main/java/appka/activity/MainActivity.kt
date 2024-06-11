package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import appka.viewmodels.factory.UserViewModelFactory
import appka.misc.AppDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.ui.theme.TowerDefenseSeminarTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.*
import androidx.compose.material.icons.filled.Add
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.SystemUIHelper.hideSystemUI

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(this)
        val db = AppDatabase.getDatabase(this)

        setContent {
            TowerDefenseSeminarTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                var isAdmin by remember { mutableStateOf(false) }
                val username = intent.getStringExtra("username") ?: ""

                val userViewModel: UserViewModel by viewModels {
                    UserViewModelFactory(db.appUserDao())
                }

                LaunchedEffect(username) {
                    userViewModel.getUser(username, "")
                    userViewModel.user.observe(this@MainActivity) { user ->
                        isAdmin = user?.isAdmin ?: false
                    }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { DrawerContent() }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBarContent(navController, onMenuClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }, onSettingsClicked = {
                                navController.navigate("settings")
                            })
                        },
                        bottomBar = { BottomNavigationBar(navController) },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                when (navController.currentBackStackEntry?.destination?.route) {
                                    "news" -> if (isAdmin) navController.navigate("addNewsItem")
                                    "hub" -> navController.navigate("addHubItem")
                                    "stats" -> navController.navigate("addStatsItem")
                                    "games" -> navController.navigate("addGameItem")
                                    "guides" -> navController.navigate("addGuideItem")
                                    else -> {}
                                }
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Add Item")
                            }
                        },
                        containerColor = Color.Black
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .border(
                                    2.dp,
                                    colorResource(id = R.color.light_blue),
                                    RoundedCornerShape(4.dp)
                                )
                                .background(colorResource(id = R.color.dark_gray))
                        ) {
                            NavHost(navController, startDestination = "news") {
                                composable("news") {
                                    val newsViewModel: NewsViewModel by viewModels {
                                        NewsViewModelFactory(db.newsItemDao())
                                    }
                                    val newsItems by newsViewModel.newsItems.collectAsState()
                                    NewsScreen(newsItems, isAdmin, { newsItem ->
                                        scope.launch {
                                            db.newsItemDao().insert(newsItem)
                                            newsViewModel.refreshNewsItems()
                                        }
                                    }, {
                                        scope.launch {
                                            newsViewModel.refreshNewsItems()
                                        }
                                    })
                                }
                                composable("hub") {
                                    val hubViewModel: HubViewModel by viewModels {
                                        HubViewModelFactory(db.hubsItemDao())
                                    }
                                    val hubItems by hubViewModel.hubItems.collectAsState()
                                    HubScreen(hubItems, isAdmin, { hubItem ->
                                        scope.launch {
                                            db.hubsItemDao().insert(hubItem)
                                            hubViewModel.refreshHubItems()
                                        }
                                    }, {
                                        scope.launch {
                                            hubViewModel.refreshHubItems()
                                        }
                                    })
                                }
                                composable("stats") {
                                    val statsViewModel: StatsViewModel by viewModels {
                                        StatsViewModelFactory(db.statsItemDao())
                                    }
                                    val statsItems by statsViewModel.statsItems.collectAsState()
                                    StatsScreen(statsItems, isAdmin, { statsItem ->
                                        scope.launch {
                                            db.statsItemDao().insertStatsItem(statsItem)
                                            statsViewModel.refreshStatsItems()
                                        }
                                    }, {
                                        scope.launch {
                                            statsViewModel.refreshStatsItems()
                                        }
                                    })
                                }
                                composable("games") {
                                    val gamesViewModel: GamesViewModel by viewModels {
                                        GamesViewModelFactory(db.gameDao())
                                    }
                                    val gamesItems by gamesViewModel.gamesItems.collectAsState()
                                    GamesScreen(gamesItems, isAdmin, { gameItem ->
                                        scope.launch {
                                            db.gameDao().insertGame(gameItem)
                                            gamesViewModel.refreshGameItems()
                                        }
                                    }, {
                                        scope.launch {
                                            gamesViewModel.refreshGameItems()
                                        }
                                    })
                                }
                                composable("guides") {
                                    val guidesViewModel: GuidesViewModel by viewModels {
                                        GuidesViewModelFactory(db.guideDao())
                                    }
                                    val guidesItems by guidesViewModel.guidesItems.collectAsState()
                                    GuidesScreen(guidesItems, isAdmin, { guideItem ->
                                        scope.launch {
                                            db.guideDao().insertGuide(guideItem)
                                            guidesViewModel.refreshGuideItems()
                                        }
                                    }, {
                                        scope.launch {
                                            guidesViewModel.refreshGuideItems()
                                        }
                                    })
                                }
                                composable("settings") {
                                    SettingsScreen(
                                        userViewModel = userViewModel,
                                        onLogOut = {
                                            finish()
                                        },
                                        onBack = {
                                            navController.navigateUp()
                                        }
                                    )
                                }

                                composable("addNewsItem") {
                                    AddNewsItemScreen(onAdd = { newsItem ->
                                        scope.launch {
                                            db.newsItemDao().insert(newsItem)
                                            navController.popBackStack()
                                        }
                                    })
                                }
                                composable("addHubItem") {
                                    AddHubItemScreen(onAdd = { hubItem ->
                                        scope.launch {
                                            db.hubsItemDao().insert(hubItem)
                                            navController.popBackStack()
                                        }
                                    })
                                }
                                composable("addStatsItem") {
                                    AddStatsItemScreen(onAdd = { statsItem ->
                                        scope.launch {
                                            db.statsItemDao().insertStatsItem(statsItem)
                                            navController.popBackStack()
                                        }
                                    })
                                }
                                composable("addGameItem") {
                                    AddGameItemScreen(onAdd = { gameItem ->
                                        scope.launch {
                                            db.gameDao().insertGame(gameItem)
                                            navController.popBackStack()
                                        }
                                    })
                                }
                                composable("addGuideItem") {
                                    AddGuideItemScreen(onAdd = { guideItem ->
                                        scope.launch {
                                            db.guideDao().insertGuide(guideItem)
                                            navController.popBackStack()
                                        }
                                    })
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
            //zatial
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(navController: NavHostController, onMenuClicked: () -> Unit, onSettingsClicked: () -> Unit) {
    val context = LocalContext.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""

    TopAppBar(
        title = {
            Text(
                getCurrentTitle(currentDestination),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.light_blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = colorResource(id = R.color.light_blue))
            }
        },
        actions = {
            IconButton(onClick = onSettingsClicked) {
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
fun NewsScreen(
    newsItems: List<NewsItem>,
    isAdmin: Boolean,
    onAddNewsItem: (NewsItem) -> Unit,
    onRefreshNewsItems: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (isAdmin) {
                IconButton(onClick = { isAdding = !isAdding }) {
                    Icon(
                        if (isAdding) Icons.Default.Remove else Icons.Default.Add,
                        contentDescription = "Add News Item",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
            }
            IconButton(onClick = { onRefreshNewsItems() }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh News Items",
                    tint = colorResource(id = R.color.light_blue)
                )
            }
        }

        if (isAdmin && isAdding) {
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
                        val currentDate = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
                        val newNewsItem = NewsItem(
                            title = title,
                            description = description,
                            date = currentDate,
                            likes = 0
                        )
                        onAddNewsItem(newNewsItem)
                        title = ""
                        description = ""
                        isAdding = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
            ) {
                Text("Add News Item", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn {
            items(newsItems) { newsItem ->
                NewsItemView(newsItem)
            }
        }
    }
}

@Composable
fun NewsItemView(newsItem: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .border(1.dp, colorResource(id = R.color.light_blue))
    ) {
        Text("Title: ${newsItem.title}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text(newsItem.description, color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text("Date: ${newsItem.date}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(8.dp))
    }
}



@Composable
fun HubScreen(
    hubItems: List<HubsItem>,
    isAdmin: Boolean,
    onAddHubItem: (HubsItem) -> Unit,
    onRefreshHubItems: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (isAdmin) {
                IconButton(onClick = { isAdding = !isAdding }) {
                    Icon(
                        if (isAdding) Icons.Default.Remove else Icons.Default.Add,
                        contentDescription = "Add Hub Item",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
            }
            IconButton(onClick = { onRefreshHubItems() }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh Hub Items",
                    tint = colorResource(id = R.color.light_blue)
                )
            }
        }

        if (isAdmin && isAdding) {
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
                        val newHubItem = HubsItem(
                            title = title,
                            description = description
                        )
                        onAddHubItem(newHubItem)
                        title = ""
                        description = ""
                        isAdding = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
            ) {
                Text("Add Hub Item", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn {
            items(hubItems) { hubItem ->
                HubItemView(hubItem)
            }
        }
    }
}

@Composable
fun HubItemView(hubItem: HubsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .border(1.dp, colorResource(id = R.color.light_blue))
    ) {
        Text("Title: ${hubItem.title}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text(hubItem.description, color = colorResource(id = R.color.light_blue))
    }
}


@Composable
fun StatsScreen(
    statsItems: List<StatsItem>,
    isAdmin: Boolean,
    onAddStatsItem: (StatsItem) -> Unit,
    onRefreshStatsItems: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var gameName by remember { mutableStateOf("") }
    var wins by remember { mutableStateOf("") }
    var losses by remember { mutableStateOf("") }

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
                IconButton(onClick = { isAdding = !isAdding }) {
                    Icon(
                        if (isAdding) Icons.Default.Remove else Icons.Default.Add,
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

            if (isAdding) {
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
                    value = wins,
                    onValueChange = { wins = it },
                    label = { Text("Wins", color = colorResource(id = R.color.light_blue)) },
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
                    value = losses,
                    onValueChange = { losses = it },
                    label = { Text("Losses", color = colorResource(id = R.color.light_blue)) },
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
                        if (gameName.isNotEmpty() && wins.isNotEmpty() && losses.isNotEmpty()) {
                            val newStatsItem = StatsItem(
                                gameName = gameName,
                                wins = wins.toInt(),
                                losses = losses.toInt()
                            )
                            onAddStatsItem(newStatsItem)
                            gameName = ""
                            wins = ""
                            losses = ""
                            isAdding = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Stats Item", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LazyColumn {
            items(statsItems) { statsItem ->
                StatsItemView(statsItem)
            }
        }
    }
}

@Composable
fun StatsItemView(statsItem: StatsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .border(1.dp, colorResource(id = R.color.light_blue))
    ) {
        Text("Game Name: ${statsItem.gameName}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text("Wins: ${statsItem.wins}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text("Losses: ${statsItem.losses}", color = colorResource(id = R.color.light_blue))
    }
}

@Composable
fun GamesScreen(
    gamesItems: List<Game>,
    isAdmin: Boolean,
    onAddGameItem: (Game) -> Unit,
    onRefreshGameItems: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var gameName by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

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
                IconButton(onClick = { isAdding = !isAdding }) {
                    Icon(
                        if (isAdding) Icons.Default.Remove else Icons.Default.Add,
                        contentDescription = "Add Game Item",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
                IconButton(onClick = { onRefreshGameItems() }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh Game Items",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
            }

            if (isAdding) {
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
                        if (gameName.isNotEmpty() && desc.isNotEmpty()) {
                            val newGameItem = Game(
                                name = gameName,
                                desc = desc
                            )
                            onAddGameItem(newGameItem)
                            gameName = ""
                            desc = ""
                            isAdding = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Game Item", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LazyColumn {
            items(gamesItems) { gameItem ->
                GameItemView(gameItem)
            }
        }
    }
}

@Composable
fun GameItemView(gameItem: Game) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .border(1.dp, colorResource(id = R.color.light_blue))
    ) {
        Text("Game Name: ${gameItem.name}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text(gameItem.desc, color = colorResource(id = R.color.light_blue))
    }
}

@Composable
fun GuidesScreen(
    guidesItems: List<Guide>,
    isAdmin: Boolean,
    onAddGuideItem: (Guide) -> Unit,
    onRefreshGuideItems: () -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

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
                IconButton(onClick = { isAdding = !isAdding }) {
                    Icon(
                        if (isAdding) Icons.Default.Remove else Icons.Default.Add,
                        contentDescription = "Add Guide Item",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
                IconButton(onClick = { onRefreshGuideItems() }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh Guide Items",
                        tint = colorResource(id = R.color.light_blue)
                    )
                }
            }

            if (isAdding) {
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
                    value = link,
                    onValueChange = { link = it },
                    label = { Text("Link", color = colorResource(id = R.color.light_blue)) },
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
                        if (title.isNotEmpty() && link.isNotEmpty()) {
                            val newGuideItem = Guide(
                                title = title,
                                link = link
                            )
                            onAddGuideItem(newGuideItem)
                            title = ""
                            link = ""
                            isAdding = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
                ) {
                    Text("Add Guide Item", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LazyColumn {
            items(guidesItems) { guideItem ->
                GuideItemView(guideItem)
            }
        }
    }
}

@Composable
fun GuideItemView(guideItem: Guide) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .border(1.dp, colorResource(id = R.color.light_blue))
    ) {
        Text("Title: ${guideItem.title}", color = colorResource(id = R.color.light_blue))
        Spacer(modifier = Modifier.height(4.dp))
        Text(guideItem.link, color = colorResource(id = R.color.light_blue))
    }
}
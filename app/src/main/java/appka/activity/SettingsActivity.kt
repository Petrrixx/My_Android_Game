package com.example.towerdefense_seminar_bolecek_peter_5zyi24

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import appka.viewmodels.factory.UserViewModelFactory
import appka.misc.AppDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.ui.theme.TowerDefenseSeminarTheme
import android.content.Intent


@OptIn(ExperimentalMaterial3Api::class)
class SettingsActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(AppDatabase.getDatabase(applicationContext).appUserDao())).get(UserViewModel::class.java)

        SystemUIHelper.hideSystemUI(this)

        val username = intent.getStringExtra("username") ?: ""
        userViewModel.getUser(username, "")

        setContent {
            TowerDefenseSeminarTheme {
                SettingsScreen(
                    userViewModel = userViewModel,
                    onLogOut = {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    },
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(userViewModel: UserViewModel, onLogOut: () -> Unit, onBack: () -> Unit) {
    val user by userViewModel.user.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SETTINGS",
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(id = R.color.light_blue)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = colorResource(id = R.color.light_blue))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_gray)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user?.let {
                Text("Username: ${it.username}", color = colorResource(id = R.color.light_blue), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nickname: ${it.nickname}", color = colorResource(id = R.color.light_blue))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Registration Date: ${it.registrationDate}", color = colorResource(id = R.color.light_blue))
                Spacer(modifier = Modifier.height(16.dp))
            } ?: run {
                Text("Loading...", color = colorResource(id = R.color.light_blue))
            }
            Button(
                onClick = onLogOut,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
            ) {
                Text("Log Out", color = Color.Black)
            }
        }
    }
}





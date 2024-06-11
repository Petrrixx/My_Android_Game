package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import appka.viewmodels.factory.UserViewModelFactory
import appka.misc.AppDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.ui.theme.TowerDefenseSeminarTheme
import kotlinx.coroutines.launch


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(this)
        SystemUIHelper.hideSystemUI(this)
        setContent {
            TowerDefenseSeminarTheme {
                val userViewModel: UserViewModel by viewModels {
                    UserViewModelFactory(db.appUserDao())
                }

                var showSnackbar by remember { mutableStateOf(false) }
                var snackbarMessage by remember { mutableStateOf("") }
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(showSnackbar) {
                    if (showSnackbar) {
                        snackbarHostState.showSnackbar(snackbarMessage)
                        showSnackbar = false
                    }
                }

                LoginScreen(
                    onLoginClick = { username, password ->
                        userViewModel.getUser(username, password)
                        lifecycleScope.launch {
                            val user = userViewModel.user.value
                            if (user != null) {
                                navigateToMain(user.isAdmin)
                            } else {
                                showSnackbar = true
                                snackbarMessage = "Invalid username or password"
                            }
                        }
                    },
                    snackbarHostState = snackbarHostState,
                    showSnackbar = showSnackbar,
                    snackbarMessage = snackbarMessage
                )
            }
        }
    }

    private fun navigateToMain(isAdmin: Boolean) {
        val context = this
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("isAdmin", isAdmin)
        }
        startActivity(intent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    snackbarHostState: SnackbarHostState,
    showSnackbar: Boolean,
    snackbarMessage: String
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = snackbarMessage,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = colorResource(id = R.color.light_blue)) },
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
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = colorResource(id = R.color.light_blue)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = null,
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                },
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onLoginClick(username, password)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
            ) {
                Text("Login", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigateToSignUp(context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
            ) {
                Text("Sign Up", color = Color.Black)
            }
        }
    }
}

fun navigateToSignUp(context: android.content.Context) {
    context.startActivity(Intent(context, SignUpActivity::class.java))
}

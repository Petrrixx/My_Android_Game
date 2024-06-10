package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.AppDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.AppUser
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.ui.theme.TowerDefenseSeminarTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class SignUpActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AppDatabase.getDatabase(this)

        window?.decorView?.post {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        setContent {
            TowerDefenseSeminarTheme {
                SignUpScreen { username, password ->
                    lifecycleScope.launch {
                        db.appUserDao().insert(AppUser(username, password))
                        navigateToLogin()
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val context = this
        context.startActivity(Intent(context, LoginActivity::class.java))
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onSignUpClick: (String, String) -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
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
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = colorResource(id = R.color.light_blue))
                }
            },
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
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", style = MaterialTheme.typography.bodyLarge, color = colorResource(id = R.color.light_blue)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = colorResource(id = R.color.light_blue))
                }
            },
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password == confirmPassword) {
                    onSignUpClick(username, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_blue))
        ) {
            Text("Sign Up", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
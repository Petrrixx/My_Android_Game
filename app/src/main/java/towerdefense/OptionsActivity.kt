package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val returnButton: Button = findViewById(R.id.return_button)
        returnButton.setOnClickListener {
            finish()
        }
    }
}

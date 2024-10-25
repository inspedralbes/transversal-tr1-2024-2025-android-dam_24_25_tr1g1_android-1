package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class HistorialComandesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historialcomandes)

        val backToUserButton = findViewById<Button>(R.id.back_to_profile_button)
        backToUserButton.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }


        val userButton = findViewById<TextView>(R.id.ordre_button)

        userButton.setOnClickListener {

            val intent = Intent(this, EstatComandaActivity::class.java)
            startActivity(intent)
        }
    }
}

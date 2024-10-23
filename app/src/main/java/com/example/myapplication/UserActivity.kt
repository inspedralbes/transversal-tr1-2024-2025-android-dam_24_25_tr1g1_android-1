package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil) // Asegúrate de que el nombre del archivo XML sea correcto

        // Configura el botón para volver al menú
        val backToMenuButton = findViewById<Button>(R.id.tornar_menu)
        backToMenuButton.setOnClickListener {
            // Crea un Intent para volver a MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish() // Finaliza esta actividad si no quieres que se quede en la pila de actividades
        }

        // Configura el botón para abrir la actividad de información personal
        val personalInfoButton = findViewById<Button>(R.id.personal_info_button)
        personalInfoButton.setOnClickListener {
            // Crea un Intent para abrir PersonalInfoActivity
            val intent = Intent(this, InfoPersonalActivity::class.java)
            startActivity(intent)
        }
    }
}

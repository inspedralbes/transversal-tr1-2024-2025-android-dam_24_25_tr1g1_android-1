package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu) // Asegúrate de que el layout se llama correctamente

        // Configura el botón del usuario
        val userButton = findViewById<ImageView>(R.id.user_button)

        userButton.setOnClickListener {
            // Crea un Intent para ir a UserActivity
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent) // Inicia UserActivity
        }

        // Configura el botón del carrito si es necesario
        val cartButton = findViewById<ImageView>(R.id.cart_button)
        cartButton.setOnClickListener {
            // Aquí puedes agregar la lógica para ir a CarritoActivity si es necesario
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
    }
}

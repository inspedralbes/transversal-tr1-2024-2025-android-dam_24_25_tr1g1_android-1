package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val userButton = findViewById<ImageView>(R.id.user_button)

        userButton.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        val detalleButton = findViewById<ImageView>(R.id.ComidaDetallada)
        detalleButton.setOnClickListener {

            val intent = Intent(this, DetalleProductoActivity::class.java)
            startActivity(intent)
        }

        val cartButton = findViewById<ImageView>(R.id.cart_button)
        cartButton.setOnClickListener {

            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
    }
}

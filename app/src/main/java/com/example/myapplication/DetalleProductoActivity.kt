package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity

class DetalleProductoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_producto)

        val cartButton = findViewById<ImageView>(R.id.cart_button)
        cartButton.setOnClickListener {

            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

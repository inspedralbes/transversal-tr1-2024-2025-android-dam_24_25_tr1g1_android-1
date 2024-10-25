package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class CarritoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrito)

        val backButton = findViewById<Button>(R.id.tornar_menu)

        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        val FerPagamentButton = findViewById<Button>(R.id.checkout_button)

        FerPagamentButton.setOnClickListener {
            val intent = Intent(this, PagoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
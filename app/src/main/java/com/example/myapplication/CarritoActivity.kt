package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.myapplication.R

class CarritoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrito)

        // Obtiene el carrito que viene desde el Intent
        val cart = intent.getParcelableArrayListExtra<Producto>("cart") ?: mutableListOf()

        // Referencias a las vistas
        val productContainer = findViewById<LinearLayout>(R.id.product_container)
        val totalPriceText = findViewById<TextView>(R.id.total_price)
        val backButton = findViewById<Button>(R.id.tornar_menu)
        val checkoutButton = findViewById<Button>(R.id.checkout_button)

        // Añade los productos al layout dinámico
        var total = 0
        for (product in cart) {
            val productTextView = TextView(this).apply {
                text = "${product.nom} - ${product.preu} €"
                textSize = 16f
                setPadding(0, 8, 0, 8)
            }
            productContainer.addView(productTextView)
            total += product.preu
        }

        // Muestra el total
        totalPriceText.text = "$total € TOTAL"

        // Botón para volver al menú
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón para proceder al pago
        checkoutButton.setOnClickListener {
            val intent = Intent(this, PagoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

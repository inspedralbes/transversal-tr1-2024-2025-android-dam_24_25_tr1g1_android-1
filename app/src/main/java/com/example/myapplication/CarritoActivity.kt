package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.myapplication.network.Producto

class CarritoActivity : ComponentActivity() {

    // Utilizaremos un mapa para almacenar productos y sus cantidades
    private val cart = mutableMapOf<Producto, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrito)

        // Obtiene el carrito que viene desde el Intent
        val productsFromIntent = intent.getParcelableArrayListExtra<Producto>("cart") ?: mutableListOf()

        // Referencias a las vistas
        val productContainer = findViewById<LinearLayout>(R.id.product_container)
        val totalPriceText = findViewById<TextView>(R.id.total_price)
        val backButton = findViewById<Button>(R.id.tornar_menu)
        val checkoutButton = findViewById<Button>(R.id.checkout_button)

        // Inicializa el carrito
        for (product in productsFromIntent) {
            addProductToCart(product)
        }

        // Muestra el total
        updateTotal()

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

    private fun addProductToCart(product: Producto) {
        // Verifica si el producto ya está en el carrito
        if (cart.containsKey(product)) {
            // Si ya está, incrementa la cantidad
            cart[product] = cart[product]!! + 1
        } else {
            // Si no está, añádelo con cantidad 1
            cart[product] = 1
        }

        // Actualiza la vista del carrito
        updateCartView()
    }

    private fun updateCartView() {
        val productContainer = findViewById<LinearLayout>(R.id.product_container)
        productContainer.removeAllViews() // Limpiar el contenedor

        // Añade los productos al layout dinámico
        for ((product, quantity) in cart) {
            val productView = layoutInflater.inflate(R.layout.carrito_item, productContainer, false)
            val productName = productView.findViewById<TextView>(R.id.product_name)
            val productPrice = productView.findViewById<TextView>(R.id.product_price)
            val addButton = productView.findViewById<Button>(R.id.add_button)
            val subtractButton = productView.findViewById<Button>(R.id.subtract_button)
            val productQuantity = productView.findViewById<TextView>(R.id.product_quantity)

            productName.text = product.nom
            productPrice.text = "${product.preu * quantity} €"
            productQuantity.text = quantity.toString()

            // Maneja el botón de sumar
            addButton.setOnClickListener {
                cart[product] = quantity + 1
                updateCartView() // Actualiza la vista
            }

            // Maneja el botón de restar
            subtractButton.setOnClickListener {
                if (quantity > 1) {
                    cart[product] = quantity - 1
                    updateCartView() // Actualiza la vista
                } else {
                    // Eliminar el producto si la cantidad es 0
                    cart.remove(product)
                    updateCartView() // Actualiza la vista
                }
            }

            productContainer.addView(productView)
        }

        // Muestra el total
        updateTotal()
    }

    private fun updateTotal() {
        val totalPriceText = findViewById<TextView>(R.id.total_price)
        val total = cart.entries.sumBy { it.key.preu * it.value } // Calcula el total
        totalPriceText.text = "$total € TOTAL"
    }
}
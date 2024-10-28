package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.myapplication.network.Producto
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class CarritoActivity : ComponentActivity() {
    private val cart: MutableMap<Producto, Int> = CartManager.cart

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.carrito)
            updateCartView()

            // Referencias a las vistas
            val productContainer = findViewById<LinearLayout>(R.id.product_container)
            val totalPriceText = findViewById<TextView>(R.id.total_price)
            val backButton = findViewById<Button>(R.id.tornar_menu)
            val checkoutButton = findViewById<Button>(R.id.checkout_button)

            // Inicializa el carrito con los productos del Intent
            val productsFromIntent = intent.getParcelableArrayListExtra<Producto>("cart") ?: mutableListOf()
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
            if (cart.containsKey(product)) {
                cart[product] = cart[product]!! + 1
            } else {
                cart[product] = 1
            }
            saveCartToPreferences(this, cart)
            updateCartView()
            logCartContents()
        }

        private fun updateCartView() {
            val productContainer = findViewById<LinearLayout>(R.id.product_container)
            productContainer.removeAllViews()

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

                addButton.setOnClickListener {
                    CartManager.addProduct(product)
                    saveCartToPreferences(this, cart)
                    updateCartView()
                    logCartContents()
                }

                subtractButton.setOnClickListener {
                    CartManager.removeProduct(product)
                    saveCartToPreferences(this, cart)
                    updateCartView()
                    logCartContents()
                }

                productContainer.addView(productView)
            }

            updateTotal()
        }

        private fun updateTotal() {
            val totalPriceText = findViewById<TextView>(R.id.total_price)
            val total = cart.entries.sumBy { it.key.preu * it.value }
            totalPriceText.text = "$total € TOTAL"
        }

        private fun logCartContents() {
            Log.d("CarritoActivity", "Contenido de la cesta: $cart")
        }
    }

fun saveCartToPreferences(context: Context, cart: Map<Producto, Int>) {
    val sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(cart)
    editor.putString("cart", json)
    editor.apply()
}

fun loadCartFromPreferences(context: Context): MutableMap<Producto, Int> {
    val sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("cart", null)
    val type = object : TypeToken<MutableMap<Producto, Int>>() {}.type
    return if (json != null) {
        try {
            gson.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            // Handle the case where the JSON is not in the expected format
            mutableMapOf()
        }
    } else {
        mutableMapOf()
    }
}
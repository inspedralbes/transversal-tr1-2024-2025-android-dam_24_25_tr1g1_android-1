package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuActivity : ComponentActivity() {

    private lateinit var productsGrid: GridLayout
    private lateinit var cartButton: ImageView
    private lateinit var userButton: ImageView
    private lateinit var exitButton: Button
    private val cart = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        productsGrid = findViewById(R.id.products_grid)
        cartButton = findViewById(R.id.cart_button)
        userButton = findViewById(R.id.user_button)
        exitButton = findViewById(R.id.exit_button)

        setupButtonListeners()
        loadProducts()
    }

    private fun setupButtonListeners() {
        userButton.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        cartButton.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putParcelableArrayListExtra("cart", ArrayList(cart))
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }

    private fun loadProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dam.inspedralbes.cat:26968")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val productList = apiService.getProductData()

                displayProducts(productList)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MenuActivity, "Error al cargar los productos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayProducts(productList: List<Producto>) {
        productsGrid.removeAllViews()

        for (product in productList) {
            val productView = layoutInflater.inflate(R.layout.product_item, null)

            val productName = productView.findViewById<TextView>(R.id.product_name)
            val productPrice = productView.findViewById<TextView>(R.id.product_price)
            val productImage = productView.findViewById<ImageView>(R.id.product_image)
            val addButton = productView.findViewById<Button>(R.id.add_button)


            productName.text = product.nom
            productPrice.text = "${product.preu}€"
            productImage.load(product.fotoRuta)

            if (product.halal == 1) productView.findViewById<ImageView>(R.id.halal_icon).visibility = ImageView.VISIBLE
            if (product.vegan == 1) productView.findViewById<ImageView>(R.id.vegan_icon).visibility = ImageView.VISIBLE
            if (product.gluten == 1) productView.findViewById<ImageView>(R.id.gluten_icon).visibility = ImageView.VISIBLE
            if (product.lactosa == 1) productView.findViewById<ImageView>(R.id.lactosa_icon).visibility = ImageView.VISIBLE
            if (product.crustacis == 1) productView.findViewById<ImageView>(R.id.crustacis_icon).visibility = ImageView.VISIBLE

            // Configurar botones de agregar al carrito
            addButton.setOnClickListener {
                cart.add(product)
                Toast.makeText(this, "${product.nom} añadido al carrito", Toast.LENGTH_SHORT).show()
            }

            // Agregar la vista del producto al GridLayout
            productsGrid.addView(productView)
        }
    }
}

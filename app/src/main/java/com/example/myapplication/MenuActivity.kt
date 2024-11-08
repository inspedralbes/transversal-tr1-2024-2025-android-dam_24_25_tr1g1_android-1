package com.example .myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.ComponentActivity
import coil.load
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.network.Interface
import com.example.myapplication.network.Producto
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.CarritoActivity
import com.example.myapplication.CartManager
import com.example.myapplication.R
import com.example.myapplication.SocketManager
import com.example.myapplication.UserActivity

class MenuActivity : ComponentActivity() {

    private lateinit var productsGrid: GridLayout
    private lateinit var cartButton: ImageView
    private lateinit var userButton: ImageView
    private lateinit var exitButton: Button
    private lateinit var numberOfProducts: TextView
    private var cart: MutableMap<Producto, Int> = CartManager.cart

    var socket= SocketManager.socket

    val changeProductesStatus = Emitter.Listener { args ->

        setupButtonListeners()
        loadProducts()

    }
    fun showOrHideCartButton(){
        println(cart)
        if(cart.size > 0){
            cartButton.visibility = ImageView.VISIBLE
            numberOfProducts.visibility = TextView.VISIBLE
            println("This is cart from the intent " +  cart)
            numberOfProducts.text = calculateNumberOfProducts(cart).toString()
        }
        else{
            cartButton.visibility = ImageView.GONE
            numberOfProducts.visibility = TextView.GONE
        }
    }
    override fun onResume() {
        super.onResume()
        val cart: MutableMap<Producto, Int> = CartManager.cart
        println("This is cart from CartManager "+cart)
        if (cart.isNotEmpty()) {
            this.cart = cart
        }
        Log.i("Cart Menu", cart.toString())
        showOrHideCartButton()

        numberOfProducts.text = calculateNumberOfProducts(cart).toString()
    }
    private fun calculateNumberOfProducts(cart:MutableMap<Producto, Int>): Int {
        var total = 0
        cart.forEach { product ->
            total += product.key.quantity
        }
        return total
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        socket!!.on("productesUpdated", changeProductesStatus)

        productsGrid = findViewById(R.id.products_grid)
        cartButton = findViewById(R.id.cart_button)
        userButton = findViewById(R.id.user_button)
        exitButton = findViewById(R.id.exit_button)
        numberOfProducts = findViewById(R.id.numberOfProducts)
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
            intent.putParcelableArrayListExtra("cart", cart.keys.toMutableList() as ArrayList<Producto>)
            startActivity(intent)

        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }

    private fun loadProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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

            val productName = productView.findViewById<TextView>(R.id.product_nom)
            val productPrice = productView.findViewById<TextView>(R.id.product_price)
            val productImage = productView.findViewById<ImageView>(R.id.product_image)
            val addButton = productView.findViewById<Button>(R.id.add_button)
            val productDesc = productView.findViewById<TextView>(R.id.product_desc)


            productName.text = product.nom
            if (product.oferta !=null) {
                productPrice.text = "${product.preu}€"
            }
            else{
                productPrice.text = "${product.oferta}€"
            }
            productImage.load(BASE_URL+ "/" + product.fotoRuta)
            productImage.setOnClickListener {
                productImage.visibility = ImageView.GONE
                productDesc.visibility = TextView.VISIBLE
            }
            productDesc.text=product.descripcio
            productView.setOnClickListener {
                productImage.visibility = TextView.VISIBLE
                productDesc.visibility = ImageView.GONE
            }

            if (product.halal == 1) productView.findViewById<ImageView>(R.id.halal_icon).visibility = ImageView.VISIBLE
            if (product.vegan == 1) productView.findViewById<ImageView>(R.id.vegan_icon).visibility = ImageView.VISIBLE
            if (product.gluten == 1) productView.findViewById<ImageView>(R.id.gluten_icon).visibility = ImageView.VISIBLE
            if (product.lactosa == 1) productView.findViewById<ImageView>(R.id.lactosa_icon).visibility = ImageView.VISIBLE
            if (product.crustacis == 1) productView.findViewById<ImageView>(R.id.crustacis_icon).visibility = ImageView.VISIBLE

            addButton.setOnClickListener {
                if(product.stock > 0){
                    CartManager.addProduct(product)
                    product.stock -= 1
                    cart = CartManager.cart
                    showOrHideCartButton()
                Toast.makeText(this, "${product.nom} afegit a la cistella", Toast.LENGTH_SHORT).show()
                    }
                else{
                    Toast.makeText(this, "Producte ${product.nom} en falta de stock", Toast.LENGTH_SHORT).show()
                }
            }


            productsGrid.addView(productView)
        }
    }
}
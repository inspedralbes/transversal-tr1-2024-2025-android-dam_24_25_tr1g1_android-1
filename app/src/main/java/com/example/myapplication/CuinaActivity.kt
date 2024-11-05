package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.CartManager.cart
import com.example.myapplication.UserManager.user
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.network.Interface
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CuinaActivity : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmaciocuina)
        val textConfirmation = findViewById<TextView>(R.id.text_confirmation)

        // Agrega una nueva comanda y actualiza el texto de confirmación
        addComanda { comanId ->
            textConfirmation.text = "ORDRE #$comanId REBUDA A CUINA!"
            updateStockForCartItems()
            CartManager.clearCart()
        }

        //Si no se puede enviar la comanda, muestra un mensaje de error
        textConfirmation.text = "ORDRE NO REBUDA A CUINA!"

        val VeureEstatButton = findViewById<Button>(R.id.view_status_button)
        VeureEstatButton.setOnClickListener {
            val intent = Intent(this, HistorialComandesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Añade la comanda a la base de datos
    private fun addComanda(callback: (Int) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val comanda = createComanda()
                Log.i("Comanda", Gson().toJson(comanda))
                val comanId = apiService.addComanda(comanda)
                Log.i("comanId", comanId.toString())
                callback(comanId)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@CuinaActivity, "Error al enviar la comanda", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Crea una comanda con los productos del carrito
    private fun createComanda(): ComandaManager.ComandaAdd {
        val contingut = cart.map {
        ComandaManager.ContingutAdd(
            it.key.id,
            it.key.nom,
            it.key.preu * it.value, // calculando el precio total
            it.value // asumiendo que `it.value` es la cantidad
        )
        }
        Log.i("Contingut", Gson().toJson(contingut))
        return ComandaManager.ComandaAdd(
            id = 0,
            client = user!!.id,
            contingut = contingut,
            preuComanda = cart.entries.sumByDouble { (it.key.preu * it.value).toDouble() }.toFloat(),
            estat = "",
            data = "",
            cancel = 0
        )
    }
    private fun updateStockForCartItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cart.forEach { (producto, cantidad) ->
                    apiService.updateStockProd(producto.id.toString(), cantidad)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@CuinaActivity, "Error al actualizar el stock", Toast.LENGTH_LONG).show()
            }
        }
    }
}
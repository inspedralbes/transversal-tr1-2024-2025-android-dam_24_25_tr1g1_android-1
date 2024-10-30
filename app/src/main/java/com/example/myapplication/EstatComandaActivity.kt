package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.network.Interface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class EstatComandaActivity : ComponentActivity() {

    private lateinit var contingutComanda: List<ComandaManager.Contingut>


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
   override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.estado_orden)

    val comandaId = intent.getIntExtra("comanda_id", -1)
    val comandaEstat = intent.getStringExtra("comanda_estat")

    val comandaIdTextView = findViewById<TextView>(R.id.id_comanda)
    val comandaEstatTextView = findViewById<TextView>(R.id.estat_comanda)
    val cancelComandaButton = findViewById<Button>(R.id.cancel_comanda_button)
    val confrimarComandaButtonTrue = findViewById<Button>(R.id.confirmation_true_button)
    val confrimarComandaButtonFalse = findViewById<Button>(R.id.confirmation_false_button)
    val confirmacioLinearLayout = findViewById<LinearLayout>(R.id.confirmacio)

        loadOneComanda(comandaId.toString())
    comandaIdTextView.text = "Ordre   #$comandaId"
    comandaEstatTextView.text = "Estat: $comandaEstat"
    cancelComandaButton.visibility = if (comandaEstat == "Rebut") View.VISIBLE else View.GONE
    confirmacioLinearLayout.visibility = View.GONE

        cancelComandaButton.setOnClickListener {
            confirmacioLinearLayout.visibility = View.VISIBLE
        }
        confrimarComandaButtonTrue.setOnClickListener {
            cancelComanda(comandaId)
            confirmacioLinearLayout.visibility = View.GONE
        }
        confrimarComandaButtonFalse.setOnClickListener {
            confirmacioLinearLayout.visibility = View.GONE
        }

    val backToHistoryButton = findViewById<Button>(R.id.back_to_history_button)
       backToHistoryButton.setOnClickListener {
        val intent = Intent(this, HistorialComandesActivity::class.java)
        startActivity(intent)
        finish()
    }
}
    private fun loadOneComanda(comandaId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getComanDataOne(comandaId) // el contingut de la comanda
                Log.i("Comanda" + comandaId, response.toString())
                contingutComanda = response
                updateContingutComandaView()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EstatComandaActivity, "Error al cargar les comandes", Toast.LENGTH_LONG).show()
            }
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun updateContingutComandaView() {
        val llistatProductes = findViewById<LinearLayout>(R.id.llistat_productes)
        llistatProductes.removeAllViews()
        var total = 0.0
        //info de cada producte de la comanda
        for (item in contingutComanda) {
            val productView = layoutInflater.inflate(R.layout.contingut_comanda_item, llistatProductes, false)
            val productQuantity = productView.findViewById<TextView>(R.id.quantitat)
            val productName = productView.findViewById<TextView>(R.id.product_nom)
            val productPrice = productView.findViewById<TextView>(R.id.preu)

            productQuantity.text = item.quantitat.toString()
            productName.text = item.nom
            productPrice.text = "${item.preuTotal} €"

            total += item.preuTotal
            llistatProductes.addView(productView)
        }

        val totalTextView = findViewById<TextView>(R.id.total)
        totalTextView.text = "TOTAL: $total €"
    }
    //funcion para cancelar la comanda. En vez de borrarse, se cambia el estado a "Cancel·lada"
    private fun cancelComanda(comandaId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.deleteConmanda(comandaId.toString(), mapOf("estat" to "Cancel·lada"))
                Log.i("Comanda" + comandaId, response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EstatComandaActivity, "Error al cancel·lar la comanda", Toast.LENGTH_LONG).show()
            }
        }
    }
}
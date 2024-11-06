package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.UserManager.user
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.network.Interface
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONObject


class HistorialComandesActivity : ComponentActivity() {

    private lateinit var comandsList: List<ComandaManager.Comanda>
    val changeComandaStatus = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        val id = data.getInt("id")
        val estat = data.getString("estat")
        println("EJOA")
        Log.i("HistorialComandesActivity", "Comanda $id updated to $estat")
        comandsList = comandsList.map {
            if (it.id == id) {
                it.estat = estat
            }
            it
        }
        runOnUiThread {
            displayFilteredComandes(false)
        }
    }



    var socket= SocketManager.socket

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        socket?.on("comandaUpdated", changeComandaStatus)
        setContentView(R.layout.historialcomandes)
        loadComandes()

        val backToUserButton = findViewById<Button>(R.id.back_to_user_button)
        backToUserButton.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }

        val userButton = findViewById<TextView>(R.id.ordre_button)
        userButton.setOnClickListener {
            val intent = Intent(this, EstatComandaActivity::class.java)
            startActivity(intent)
        }

        val comandesActualsButton = findViewById<TextView>(R.id.comandes_actuals)
        val comandesAntiguesButton = findViewById<TextView>(R.id.comandes_antigues)

        comandesActualsButton.setTextColor(Color.GRAY)

        comandesActualsButton.setOnClickListener {
            comandesActualsButton.setTextColor(Color.GRAY)
            comandesAntiguesButton.setTextColor(Color.WHITE)
            displayFilteredComandes(false)
        }

        comandesAntiguesButton.setOnClickListener {
            comandesAntiguesButton.setTextColor(Color.GRAY)
            comandesActualsButton.setTextColor(Color.WHITE)
            displayFilteredComandes(true)
        }
    }

    private fun loadComandes() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getComanData(user!!.id.toString())
                comandsList = response
                Log.i("HistorialComandesActivity", "Comandes: $comandsList")
                displayFilteredComandes(false) // Display non-Rebut comandes by default
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@HistorialComandesActivity, "Error al cargar les comandes", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayComandes(comandsList: List<ComandaManager.Comanda>) {
        val comandesContainer = findViewById<LinearLayout>(R.id.histiory_comandes)
        comandesContainer.removeAllViews()

        for (comanda in comandsList) {
            val comandaView = layoutInflater.inflate(R.layout.comanda_item, null)

            val ordreTextView = comandaView.findViewById<TextView>(R.id.ordre_text)
            val estatTextView = comandaView.findViewById<TextView>(R.id.estat_text)

            ordreTextView.text = "Ordre #${comanda.id}"
            estatTextView.text = "Estat: ${comanda.estat}"
            Log.i("cancel", comanda.cancel.toString())

            comandaView.setOnClickListener {
                val intent = Intent(this, EstatComandaActivity::class.java).apply {
                    putExtra("comanda_id", comanda.id)
                    putExtra("comanda_estat", comanda.estat)
                    // Add other details as needed
                }
                startActivity(intent)
            }

            comandesContainer.addView(comandaView)
        }
    }
//filtrat de comandes. A Antigues es mostren les comandes rebutjades o recollides (Recollit i Cancel·lada), a Actuals les que no ho estan.
    private fun displayFilteredComandes(showRebut: Boolean) {
        val filteredComandes = if (showRebut) {
            comandsList.filter { it.estat.equals("Recollit", ignoreCase = true) && it.cancel == 0}
        } else {
            comandsList.filter { !it.estat.equals("Recollit", ignoreCase = true) && it.cancel == 0}
        }
        displayComandes(filteredComandes)
    }
}
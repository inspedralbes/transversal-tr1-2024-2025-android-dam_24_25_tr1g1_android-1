package com.example.myapplication

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
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.myapplication.SocketManager

class HistorialComandesActivity : ComponentActivity() {

    private lateinit var comandsList: List<ComandaManager.Comanda>
    val changeComandaStatus = Emitter.Listener { args ->
        val data = args
        comandsList = comandsList.map {
            if (it.id == data[0]) {
                it.estat = data[1].toString()
            }
            it
        }
        loadComandes()
    }
    var socket= SocketManager.socket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        socket?.on("ChangeComanda", changeComandaStatus)
        setContentView(R.layout.historialcomandes)
        loadComandes()

        val backToUserButton = findViewById<Button>(R.id.back_to_profile_button)
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

    private fun displayFilteredComandes(showRebut: Boolean) {
        val filteredComandes = if (showRebut) {
            comandsList.filter { it.estat.equals("Recollit", ignoreCase = true) }
        } else {
            comandsList.filter { !it.estat.equals("Recollit", ignoreCase = true) }
        }
        displayComandes(filteredComandes)
    }
}
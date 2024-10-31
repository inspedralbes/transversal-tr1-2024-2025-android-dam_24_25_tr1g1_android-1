package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import io.socket.emitter.Emitter

class EstatComandaActivity : ComponentActivity() {
    var socket= SocketManager.socket
    val changeComandaStatus = Emitter.Listener { args ->
        val data = args
//        comandsList = comandsList.map {
//            if (it.id == data[0]) {
//                it.estat = data[1].toString()
//            }
//            it
//        }
//        loadComanda()
    }

   @SuppressLint("MissingInflatedId")
   override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)

    socket?.on("comandaUpdated", changeComandaStatus)
    setContentView(R.layout.estado_orden)

    val comandaId = intent.getIntExtra("comanda_id", -1)
    val comandaEstat = intent.getStringExtra("comanda_estat")

    val comandaIdTextView = findViewById<TextView>(R.id.id_comanda)
    val comandaEstatTextView = findViewById<TextView>(R.id.estat_comanda)

    comandaIdTextView.text = "Comanda ID #$comandaId"
    comandaEstatTextView.text = "Estat: $comandaEstat"

    val backToMenuButton = findViewById<Button>(R.id.back_to_menu_button)
    backToMenuButton.setOnClickListener {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
}
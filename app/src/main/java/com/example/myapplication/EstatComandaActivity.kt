package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class EstatComandaActivity : ComponentActivity() {

   @SuppressLint("MissingInflatedId")
   override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.estado_orden)

    val comandaId = intent.getIntExtra("comanda_id", -1)
    val comandaEstat = intent.getStringExtra("comanda_estat")

    val comandaIdTextView = findViewById<TextView>(R.id.id_comanda)
    val comandaEstatTextView = findViewById<TextView>(R.id.estat_comanda)
       val cancelComandaButton = findViewById<Button>(R.id.cancel_comanda_button)

    comandaIdTextView.text = "Comanda ID #$comandaId"
    comandaEstatTextView.text = "Estat: $comandaEstat"
       cancelComandaButton.visibility = if (comandaEstat == "Rebut") View.VISIBLE else View.GONE



    val backToMenuButton = findViewById<Button>(R.id.back_to_menu_button)
    backToMenuButton.setOnClickListener {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
}
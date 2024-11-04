package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class UserActivity : ComponentActivity() {
    var socket= SocketManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)

        val personalInfoButton = findViewById<Button>(R.id.personal_info_button)
        personalInfoButton.setOnClickListener {
            val intent = Intent(this, InfoPersonalActivity::class.java)
            startActivity(intent)
            finish()
        }
        val HistorialComButton = findViewById<Button>(R.id.order_history_button)
        HistorialComButton.setOnClickListener {
            val intent = Intent(this, HistorialComandesActivity::class.java)
            startActivity(intent)
            finish()
        }
        val backToMenuButton = findViewById<Button>(R.id.tornar_menu)
        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val tancarSessióButton = findViewById<Button>(R.id.tancar_sessio)
        tancarSessióButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            CartManager.clearCart()
            UserManager.clearUser()
            socket.disconnectSocket()
            finish()
        }
    }
}

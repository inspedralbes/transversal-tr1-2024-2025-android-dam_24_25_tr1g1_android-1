package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class PagoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagament)

        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
            finish()
        }
        val PayNowButton = findViewById<Button>(R.id.pay_now_button)

        PayNowButton.setOnClickListener {
            val intent = Intent(this, CuinaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
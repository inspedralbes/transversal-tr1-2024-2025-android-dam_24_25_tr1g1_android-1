package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class CuinaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmaciocuina)

        val VeureEstatButton = findViewById<Button>(R.id.view_status_button)
        VeureEstatButton.setOnClickListener {

            val intent = Intent(this, EstatComandaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
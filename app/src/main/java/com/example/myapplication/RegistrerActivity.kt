package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrer) // Asegúrate de que este sea el nombre correcto de tu archivo XML

        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.password)
        val confirmPasswordField: EditText = findViewById(R.id.confirm_password)
        val createAccountButton: Button = findViewById(R.id.login_button) // Cambia el ID si es necesario
        val enerereButton: Button = findViewById(R.id.enerere_button) // Usando el ID correcto

        createAccountButton.setOnClickListener {
            // Lógica para crear cuenta
        }

        enerereButton.setOnClickListener {
            // Regresa a la MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual para que no vuelva a esta al presionar el botón "Atrás"
        }
    }
}

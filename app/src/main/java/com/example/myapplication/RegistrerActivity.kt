package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrer)

        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.password)
        val confirmPasswordField: EditText = findViewById(R.id.confirm_password)
        val createAccountButton: Button = findViewById(R.id.login_button) // Cambia el ID si es necesario
        val enerereButton: Button = findViewById(R.id.enerere_button) // Usando el ID correcto

        // Referencias a las imágenes de alérgenos
        val allergen1Image: ImageView = findViewById(R.id.allergen1_image)
        val allergen2Image: ImageView = findViewById(R.id.allergen2_image)
        val allergen3Image: ImageView = findViewById(R.id.allergen3_image)
        val allergen4Image: ImageView = findViewById(R.id.allergen4_image)
        val allergen5Image: ImageView = findViewById(R.id.allergen5_image)

        createAccountButton.setOnClickListener {
            // Lógica para crear cuenta
        }

        enerereButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Añadir OnClickListeners a cada imagen de alérgenos
        allergen1Image.setOnClickListener { toggleSelection(allergen1Image) }
        allergen2Image.setOnClickListener { toggleSelection(allergen2Image) }
        allergen3Image.setOnClickListener { toggleSelection(allergen3Image) }
        allergen4Image.setOnClickListener { toggleSelection(allergen4Image) }
        allergen5Image.setOnClickListener { toggleSelection(allergen5Image) }
    }

    private fun toggleSelection(imageView: ImageView) {
        imageView.isSelected = !imageView.isSelected
    }
}

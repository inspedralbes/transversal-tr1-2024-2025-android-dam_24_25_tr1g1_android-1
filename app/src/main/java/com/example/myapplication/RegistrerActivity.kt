package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.network.Interface
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrer)

        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.password)
        val confirmPasswordField: EditText = findViewById(R.id.confirm_password)
        val createAccountButton: Button = findViewById(R.id.login_button)
        val enerereButton: Button = findViewById(R.id.enerere_button)

        val allergen1Image: ImageView = findViewById(R.id.allergen1_image)
        val allergen2Image: ImageView = findViewById(R.id.allergen2_image)
        val allergen3Image: ImageView = findViewById(R.id.allergen3_image)
        val allergen4Image: ImageView = findViewById(R.id.allergen4_image)
        val allergen5Image: ImageView = findViewById(R.id.allergen5_image)

        createAccountButton.setOnClickListener {
            if(passwordField.text.toString() != confirmPasswordField.text.toString()){

                // Mostrar un mensaje de error o realizar alguna acción
                Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://pregrillgrab.dam.inspedralbes.cat:26968")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val apiService = retrofit.create(Interface::class.java)

                var user = User().apply {
                    nom = emailField.text.toString()
                    correu = emailField.text.toString()
                    contrasenya = hashPassword(passwordField.text.toString())
                    halal = if (allergen1Image.isSelected) 1 else 0
                    vegan = if (allergen2Image.isSelected) 1 else 0
                    gluten = if (allergen3Image.isSelected) 1 else 0
                    lactosa = if (allergen4Image.isSelected) 1 else 0
                    crustacis = if (allergen5Image.isSelected) 1 else 0
                }


                lifecycleScope.launch {

                    val userData = apiService.registerUser(user = user)
                    user=userData[0]


                    if (user.id != 0) {

                        val intent = Intent(this@RegisterActivity, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        Toast.makeText(this@RegisterActivity, "Error: Server Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        enerereButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        allergen1Image.setOnClickListener { toggleSelection(allergen1Image) }
        allergen2Image.setOnClickListener { toggleSelection(allergen2Image) }
        allergen3Image.setOnClickListener { toggleSelection(allergen3Image) }
        allergen4Image.setOnClickListener { toggleSelection(allergen4Image) }
        allergen5Image.setOnClickListener { toggleSelection(allergen5Image) }
    }
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun toggleSelection(imageView: ImageView) {
        imageView.isSelected = !imageView.isSelected
    }
}

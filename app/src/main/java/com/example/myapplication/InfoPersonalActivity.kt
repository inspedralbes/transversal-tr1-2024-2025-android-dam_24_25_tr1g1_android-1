package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.network.Interface
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

class InfoPersonalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canviarlogin)

        // Referencias a las imágenes de alérgenos
        val allergen1Image: ImageView = findViewById(R.id.allergen1_image)
        val allergen2Image: ImageView = findViewById(R.id.allergen2_image)
        val allergen3Image: ImageView = findViewById(R.id.allergen3_image)
        val allergen4Image: ImageView = findViewById(R.id.allergen4_image)
        val allergen5Image: ImageView = findViewById(R.id.allergen5_image)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dam.inspedralbes.cat:26968")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(Interface::class.java)
        val backToUserButton = findViewById<Button>(R.id.enerere_button)
        val sendButton = findViewById<Button>(R.id.aplicar_canvis)
        val password= findViewById<EditText>(R.id.password)
        val confirmPassword= findViewById<EditText>(R.id.confirm_password)
        val user = UserManager.user
        sendButton.setOnClickListener{
            if(password.text.toString()==confirmPassword.text.toString()){
                if(password.text.toString().length<1){
                    Toast.makeText(this@InfoPersonalActivity, "Error: La contrasenya no pot ser buida", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                } else{
                    lifecycleScope.launch {
                        apiService.updateUser(
                            user?.id.toString(),
                            mapOf("contrasenya" to hashPassword(password.text.toString()),
                                "halal" to if (allergen1Image.isSelected) "1" else "0",
                                "vegan" to if (allergen2Image.isSelected) "1" else "0",
                                "gluten" to if (allergen3Image.isSelected) "1" else "0",
                                "lactosa" to if (allergen4Image.isSelected) "1" else "0",
                                "crustacis" to if (allergen5Image.isSelected) "1" else "0")
                        )
                    }
                }
            }
            else{
                Toast.makeText(this@InfoPersonalActivity, "Error: Les contrasenyes no coincideixen", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
        backToUserButton.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
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
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
    private fun toggleSelection(imageView: ImageView) {
        imageView.isSelected = !imageView.isSelected
    }
}
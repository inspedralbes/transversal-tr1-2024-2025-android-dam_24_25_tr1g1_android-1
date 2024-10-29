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
            val url = URL("http://pregrillgrab.dam.inspedralbes.cat:26968/register")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.doOutput = true
            val requestBody = """
                {
                    "email": "${emailField.text}",
                    "password": "${hashPassword(passwordField.text.toString())}",
                    "allergens": [
                        ${allergen1Image.isSelected},
                        ${allergen2Image.isSelected},
                        ${allergen3Image.isSelected},
                        ${allergen4Image.isSelected},
                        ${allergen5Image.isSelected}
                    ]
                }
            """.trimIndent()
            val outputStream = OutputStreamWriter(connection.outputStream)
            outputStream.write(requestBody)
            outputStream.flush()

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

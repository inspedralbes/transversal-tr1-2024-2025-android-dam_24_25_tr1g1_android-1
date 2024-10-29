package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.network.Interface
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailField: EditText = findViewById(R.id.email)
        val passwordField: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login_button)
        val enerereButton: Button = findViewById(R.id.enerere_button)

        loginButton.setOnClickListener {
            val gson = Gson()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dam.inspedralbes.cat:26968")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(Interface::class.java)
            var user = User().apply {
                correu = emailField.text.toString()
                contrasenya = hashPassword(passwordField.text.toString())
            }

            lifecycleScope.launch {
                val userData = apiService.loginUser(user = user)


                user=userData[0]
                if (user.id != 0) {

                    val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Error: Tu Server", Toast.LENGTH_LONG).show()
                }
            }


        }

        enerereButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

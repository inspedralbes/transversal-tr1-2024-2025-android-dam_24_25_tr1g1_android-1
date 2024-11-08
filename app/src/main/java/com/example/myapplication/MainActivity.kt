package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.SocketManager.connectSocket
import com.example.myapplication.network.Producto
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val cart = remember { mutableStateListOf<Producto>() }
                connectSocket()
                NavHost(navController = navController, startDestination = "menu") {
                    composable("menu") { MenuScreen(navController,cart) }
                    composable("cart") { CartScreen(navController, cart) }
                    composable("description") {Description(navController, cart)
                    }

                }
            }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowXmlLayout()
                }
            }
        }
    }
    //Función para cargar la pantalla de inicio
    @Composable
    fun ShowXmlLayout(modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.inicio, null).apply {
                    findViewById<Button>(R.id.login_button).setOnClickListener {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                    findViewById<Button>(R.id.new_account_button).setOnClickListener {
                        val intent = Intent(context, RegisterActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }
        )
    }


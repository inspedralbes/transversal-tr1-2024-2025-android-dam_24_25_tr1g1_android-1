package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val cart = remember { mutableStateListOf<Producto>() }
                NavHost(navController = navController, startDestination = "menu") {
                    composable("menu") { MenuScreen(navController,cart) }
                    composable("cart") { CartScreen(navController, cart) }
                    composable("profile") { ProfileScreen(navController) }
                    composable("description") {Description(navController, cart)
                    }

                }
            }
        }
    }
}

// CartScreen.kt
package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CartScreen(navController: NavController) {
    val cart = remember { mutableStateListOf<Producto>() }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Productos en la Cesta",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cart.isEmpty()) {
            Text(text = "La cesta está vacía", modifier = Modifier.padding(16.dp))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(cart) { product ->
                    ProductItem(product = product, cart)
                }
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Volver al Menú")
        }
    }
}

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
fun CartScreen(navController: NavController, cart: MutableList<Producto>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Productes dins la cesta;",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cart.isEmpty()) {
            Text(text = "VACIA", modifier = Modifier.padding(16.dp))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
            Text(text = "Menú")
        }
    }
}

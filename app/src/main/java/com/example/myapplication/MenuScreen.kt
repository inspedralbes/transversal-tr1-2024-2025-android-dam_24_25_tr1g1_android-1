package com.example.myapplication

import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Composable
fun MenuScreen(navController: NavController, cart: MutableList<Producto>) {
    var products by remember { mutableStateOf<List<Producto>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:23333")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(Interface::class.java)

    LaunchedEffect(Unit) {
        try {
            products = apiService.getProductData()
        } catch (e: Exception) {
            errorMessage = "Error al cargar los productos"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Text(text = "cargando los productos (FUNCIONARA)")
        } else if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", modifier = Modifier.padding(16.dp))
        } else {
            products?.let { productList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp)
                ) {
                    items(productList) { product ->
                        ProductItem(product = product, cart)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("cart") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Cesta")
        }
    }
}

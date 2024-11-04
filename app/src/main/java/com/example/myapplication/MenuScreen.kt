package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.network.BASE_URL
import com.example.myapplication.network.Interface
import com.example.myapplication.network.Producto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var products by mutableStateOf<List<Producto>?>(null)

@Composable
fun MenuScreen(navController: NavController, cart: MutableList<Producto>) {
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Benvingut",
                    modifier = Modifier.padding(top = 50.dp, start = 90.dp)
                )
                Button(
                    onClick = { navController.navigate("profile") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.perfil),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }


            products?.let { productList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp)
                ) {
                    items(productList) { product ->
                        ProductItem(navController = navController, product = product, cart = cart)
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

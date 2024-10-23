package com.example.myapplication

import android.inputmethodservice.Keyboard
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dam.inspedralbes.cat:25959")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(Interface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductListScreen(innerPadding)
                }
            }
        }
    }

    @Composable
    fun ProductListScreen(innerPadding: PaddingValues) {
        var products by remember { mutableStateOf<List<Producto>?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            try {
                products = apiService.getProductData()
            } catch (e: Exception) {
                errorMessage = "Error al cargar los productos"
            } finally {
                isLoading = false
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
        } else if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", modifier = Modifier.padding(innerPadding))
        } else {
            products?.let { productList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Número de columnas (2 en este caso)
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(productList){ product ->
                                    ProductItem(
                                        product = product,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                }
            }


    @Composable
    fun ProductItem(product: Producto, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally // Centra todo horizontalmente

        ) {
            Image(
                painter = rememberAsyncImagePainter(product.fotoRuta),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(10.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = " ${product.nom}")
            Text(text = " ${product.preu} €")
            if (product.halal == 1) {
                Text(text = " Halal")
            } else if (product.vegan == 1) {
                Text(text = " Vegà")
            } else if (product.gluten == 1) {
                Text(text = " Gluten")
            }else if (product.lactosa == 1) {
                Text(text = " Lactosa")
            }else if (product.crustacis == 1) {
                Text(text = " Crustacis")
            }



            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

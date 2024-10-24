package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun CartScreen(navController: NavController, cart: MutableList<Producto>) {
    val productsIdSame = cart.distinctBy { it.id }
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
        items(productsIdSame) { product ->
            val count = cart.count { it.id == product.id }

            ProductCart(product = product, count = count, cart)
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
        @Composable
        fun ProductCart(
            product: Producto,
            count: Int ,
            cart: MutableList<Producto>,
            modifier: Modifier = Modifier
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.fotoRuta),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(100.dp),
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
                } else if (product.lactosa == 1) {
                    Text(text = " Lactosa")
                } else if (product.crustacis == 1) {
                    Text(text = " Crustacis")
                }
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                ) {
                    Button(
                        onClick = {
                            cart.remove(product)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        Text(text = "-")
                    }
                     Text(
                    text = count.toString(),
                    modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 16.dp)
                    )

                    Button(
                        onClick = {
                            cart.add(product)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        Text(text = "+")
                    }

                }

            }

        }





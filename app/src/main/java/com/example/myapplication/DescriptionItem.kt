package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.network.Producto

@Composable
fun Description(navController: NavController, cart: MutableList<Producto>) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "DESCRIPCION",
            modifier = Modifier.padding(top = 50.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(cart) { id ->
                id.id

                DescriptionProduct(product = id, cart)
            }
        }
    }
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Menú")
    }

}
        @Composable
        fun DescriptionProduct(
            product: Producto,
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
                            cart.add(product)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        Text(text = "Afegir")
                    }

                }
            }
        }





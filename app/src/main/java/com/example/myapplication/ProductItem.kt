package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductItem(product: Producto, cart: MutableList<Producto>, modifier: Modifier = Modifier) {
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

        Button(
            onClick = {
                cart.add(product)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Afegir")
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

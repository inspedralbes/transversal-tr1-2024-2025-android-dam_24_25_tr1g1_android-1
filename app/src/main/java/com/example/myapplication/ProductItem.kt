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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.network.Producto

@Composable
fun ProductItem(navController: NavController, product: Producto, cart: MutableList<Producto>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.fotoRuta),
            contentDescription = null,
            modifier = Modifier.width(80.dp).height(80.dp),
            contentScale = ContentScale.FillWidth
        )
        Button(
            { navController.navigate("description") },
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = " ${product.nom}")

        }
        Text(text = " ${product.preu} €")
        Row(
            modifier = Modifier
                .padding(top = 8.dp),
        ) {

            if (product.halal == 1) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.halal),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentScale = ContentScale.FillWidth
                )
            } else if (product.vegan == 1) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.vegan),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentScale = ContentScale.FillWidth
                )
            } else if (product.gluten == 1) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.gluten),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentScale = ContentScale.FillWidth
                )
            } else if (product.lactosa == 1) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.lacteos),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentScale = ContentScale.FillWidth
                )
            } else if (product.crustacis == 1) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.crustaceo),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp).height(20.dp),
                    contentScale = ContentScale.FillWidth
                )
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
}
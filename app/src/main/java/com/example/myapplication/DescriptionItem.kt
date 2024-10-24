package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Description(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "PERFIL c:",
            modifier = Modifier.padding(top = 50.dp)
        )

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Menú")
        }


    }


}
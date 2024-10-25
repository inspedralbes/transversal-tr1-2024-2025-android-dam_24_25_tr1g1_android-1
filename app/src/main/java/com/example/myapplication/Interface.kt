package com.example.myapplication
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import retrofit2.http.GET

interface Interface {
    @GET("/getProd")
    suspend fun getProductData(): List<Producto>
}
@Parcelize
data class Producto(
    val id: Int,
    val nom: String,
    val descripcio: String,
    val fotoRuta: String,
    val preu: Int,
    val oferta: Int,
    val stock: Int,
    val category: Int,
    val halal: Int ,
    val vegan: Int,
    val gluten: Int,
    val lactosa: Int,
    val crustacis:Int
) : Parcelable
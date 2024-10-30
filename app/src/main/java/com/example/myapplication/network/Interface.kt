package com.example.myapplication.network
import android.os.Parcelable
import com.example.myapplication.ComandaManager
import kotlinx.parcelize.Parcelize
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

val BASE_URL = "http://192.168.1.136:26968"

interface Interface {
    @GET("/getProd")
    suspend fun getProductData(): List<Producto>
    @GET("/getComan")
    suspend fun getComanData(): List<ComandaManager.Comanda>
    @POST("/addComan")
    suspend fun addComanda(@Body comanda: ComandaManager.ComandaAdd): Int
}
@Parcelize
data class Producto(
    val id: Int,
    val nom: String,
    val descripcio: String,
    val fotoRuta: String,
    val preu: Int,
    val oferta: Int,
    var stock: Int,
    val category: Int,
    val halal: Int ,
    val vegan: Int,
    val gluten: Int,
    val lactosa: Int,
    val crustacis:Int
) : Parcelable
package com.example.myapplication.network
import android.os.Parcelable
import com.example.myapplication.ComandaManager
import com.example.myapplication.User
import kotlinx.parcelize.Parcelize
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

val BASE_URL = "http://dam.inspedralbes.cat:26968"

interface Interface {
    @GET("/getProd")
    suspend fun getProductData(): List<Producto>
    @GET("/getComan")
    suspend fun getComanData(): List<ComandaManager.Comanda>
    @POST("/addComan")
    suspend fun addComanda(@Body comanda: ComandaManager.ComandaAdd): Int

    @POST("/addUser")
    suspend fun registerUser(
        @Body user: User
    ): List<User>

    @POST("/login")
    suspend fun loginUser(
        @Body user: User
    ): List<User>
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
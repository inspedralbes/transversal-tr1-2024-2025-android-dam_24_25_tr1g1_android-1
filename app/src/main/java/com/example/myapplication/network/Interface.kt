package com.example.myapplication.network
import android.os.Parcelable
import com.example.myapplication.ComandaManager
import com.example.myapplication.User
import com.example.myapplication.UserManager.user
import kotlinx.parcelize.Parcelize
import okhttp3.internal.userAgent
import org.json.JSONObject
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

val BASE_URL = "http://pregrillgrab.dam.inspedralbes.cat:26968"

interface Interface {
    //GET tots els productes
    @GET("/getProd")
    suspend fun getProductData(): List<Producto>
    //GET productes per client
    @GET("/getComanClient/{userId}")
    suspend fun getComanData(@Path("userId") userId: String): List<ComandaManager.Comanda>
    @GET("/getComan/{id}")
    suspend fun getOneComan(@Path("id") id: String): List<ComandaManager.Comanda>
    //GET Contingut de una comanda
    @GET("/getComanContent/{Id}")
    suspend fun getComanDataOne(@Path("Id") userId: String): List<ComandaManager.Contingut>
    //POST afegir nova comanda
    @POST("/addComan")
    suspend fun addComanda(@Body comanda: ComandaManager.ComandaAdd): Int
    //PUT UPDATE estat de la comanda
    @PUT("/delComan/{Id}")
    suspend fun deleteConmanda(@Path("Id") id: String)
    @PUT("/stockProd/{Id}")
    suspend fun updateStockProd(@Path("Id") id: String, @Body stock: Int)

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
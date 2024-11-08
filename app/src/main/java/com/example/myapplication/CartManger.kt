package com.example.myapplication

import com.example.myapplication.network.Producto

object CartManager {
    val cart: MutableMap<Producto, Int> = mutableMapOf()

    fun addProduct(product: Producto) {
        println("EMPEZAMOS A AÑADIR PRODUCTO")

            println("CART NO ESTÁ VACIO")
            var found=false
        cart.keys.forEach {
            println("ESTA EN EL FOR EACH")

            if(it.id == product.id){
                found=true
                println("ESTA EN EL IF")
                println("ESTE ES MI IT"+it )
                println("este es el cart"+cart[product])
                it.quantity+=1
            }
        }
        if(!found){
            println("NO ESTA EN EL IF")
            product.quantity=1
            cart[product] = 1
        }
    }

    fun removeProduct(product: Producto) {

        println("EMPEZAMOS A AÑADIR PRODUCTO")

        println("CART NO ESTÁ VACIO")
        var found=false
        cart.keys.forEach {
            println("ESTA EN EL FOR EACH")

            if(it.id == product.id){
                found=true
                println("ESTA EN EL IF")
                println("ESTE ES MI IT"+it )
                println("este es el cart"+cart[product])
                it.quantity-=1

            }
        }


        if (product.quantity == 0) {
            println("SE VA A ELIMINAR " + product)
            cart.entries.removeIf { it.key.id == product.id }
            cart.keys.forEach {
                println(it)
            }
        }
        println("TERMINAMOS DE AÑADIR PRODUCTO")
        println("CART" + cart)
    }

    fun clearCart() {
        cart.clear()
    }

    fun getTotalPrice(): Float {
        return cart.entries.sumByDouble { (it.key.preu * it.key.quantity).toDouble() }.toFloat()
    }
}
package com.example.myapplication

import com.example.myapplication.network.Producto

object CartManager {
    val cart: MutableMap<Producto, Int> = mutableMapOf()

    fun addProduct(product: Producto) {
        if (cart.containsKey(product)) {
            cart[product] = cart[product]!! + 1
        } else {
            cart[product] = 1
        }
    }

    fun removeProduct(product: Producto) {
        if (cart.containsKey(product)) {
            if (cart[product]!! > 1) {
                cart[product] = cart[product]!! - 1
            } else {
                cart.remove(product)
            }
        }
    }

    fun clearCart() {
        cart.clear()
    }

    fun getTotalPrice(): Int {
        return cart.entries.sumBy { it.key.preu * it.value }
    }
}
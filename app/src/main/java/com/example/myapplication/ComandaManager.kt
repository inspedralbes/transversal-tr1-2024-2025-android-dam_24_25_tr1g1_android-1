package com.example.myapplication

import com.google.gson.annotations.JsonAdapter


object ComandaManager {

    data class Comanda(
        val id: Int,
        val data: String?,
        @JsonAdapter(ContingutDeserializer::class)
        val contingut: List<Contingut>?,
        var estat: String,
        val client: Int,
        val cancel: Int

    )

    data class Contingut(
        val id: Int,
        val nom: String,
        val preuTotal: Float,
        val quantitat: Int
    )

    data class ComandaAdd(
        val id: Int,
        val data: String?,
        val contingut: List<ContingutAdd>,
        val preuComanda: Float,
        val estat: String,
        val client: Int,
        val cancel: Int
    )

    data class ContingutAdd(
        val id: Int,
        val nom: String,
        val preuTotal: Float,
        val quantitat: Int
    )
}
package com.example.myapplication

import com.google.gson.annotations.JsonAdapter


object ComandaManager {

    data class Comanda(
        val id: Int,
        val data: String?,
        @JsonAdapter(ContingutDeserializer::class)
        val contingut: List<Contingut>?,
        var estat: String,
        val client: Int

    )

    data class Contingut(
        val id: Int,
        val nom: String,
        val preuTotal: Int,
        val quantitat: Int
    )

    data class ComandaAdd(
        val id: Int,
        val data: String?,
        val contingut: List<ContingutAdd>,
        val estat: String,
        val client: Int
    )

    data class ContingutAdd(
        val id: Int,
        val nom: String,
        val preuTotal: Int,
        val quantitat: Int
    )
}
package com.example.myapplication

import com.google.gson.*
import java.lang.reflect.Type

//Archivo necesario, no se puede eliminar

class ContingutDeserializer : JsonDeserializer<List<ComandaManager.Contingut>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<ComandaManager.Contingut>? {
        return if (json.isJsonArray) {
            context.deserialize(json, typeOfT)
        } else {
            null
        }
    }
}
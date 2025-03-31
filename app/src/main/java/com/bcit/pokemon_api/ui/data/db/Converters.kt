package com.bcit.pokemon_api.ui.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.bcit.pokemon_api.ui.data.model.Sprites
import com.bcit.pokemon_api.ui.data.model.TypeSlot

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromSpritesToString(sprites: Sprites?): String? {
        return gson.toJson(sprites)
    }
    
    @TypeConverter
    fun fromStringToSprites(spritesString: String?): Sprites? {
        if (spritesString == null) return null
        val type = object : TypeToken<Sprites>() {}.type
        return gson.fromJson(spritesString, type)
    }
    
    @TypeConverter
    fun fromTypeSlotListToString(typeSlots: List<TypeSlot>?): String? {
        return gson.toJson(typeSlots)
    }
    
    @TypeConverter
    fun fromStringToTypeSlotList(typeSlotString: String?): List<TypeSlot>? {
        if (typeSlotString == null) return null
        val type = object : TypeToken<List<TypeSlot>>() {}.type
        return gson.fromJson(typeSlotString, type)
    }
    
    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        if (value == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
    
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return gson.toJson(list)
    }
}
package com.example.movieapp.local

import androidx.room.TypeConverter
import com.example.movieapp.model.moviedetail.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringToListOfGenre(data: String?): List<Genre?>? {
        if (data == null) return null
        val listType = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromListOfGenreToString(objects: List<Genre?>?): String? {
        return gson.toJson(objects)
    }

    @TypeConverter
    fun fromStringToListOfSpokenLanguage(data: String?): List<SpokenLanguage?>? {
        if (data == null) return null
        val listType = object : TypeToken<List<SpokenLanguage?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromListOfSpokenLanguageToString(objects: List<SpokenLanguage?>?): String? {
        return gson.toJson(objects)
    }

    @TypeConverter
    fun fromStringToListOfProductionCompany(data: String?): List<ProductionCompany?>? {
        if (data == null) return null
        val listType = object : TypeToken<List<ProductionCompany?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromListOfProductionCompanyToString(objects: List<ProductionCompany?>?): String? {
        return gson.toJson(objects)
    }

    @TypeConverter
    fun fromStringToListOfProductionCountry(data: String?): List<ProductionCountry?>? {
        if (data == null) return null
        val listType = object : TypeToken<List<ProductionCountry?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromListOfProductionCountryToString(objects: List<ProductionCountry?>?): String? {
        return gson.toJson(objects)
    }

    @TypeConverter
    fun fromStringToBelongsToCollection(data: String?): BelongsToCollection? {
        return data?.let {
            gson.fromJson(it, BelongsToCollection::class.java)
        }
    }

    @TypeConverter
    fun fromBelongsToCollectionToString(belongsToCollection: BelongsToCollection?): String? {
        return gson.toJson(belongsToCollection)
    }
}

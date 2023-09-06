/*
 * MIT License
 *
 * Copyright (c) 2022 Nauman Khaliq
 */
package com.nk.currencyrates.model.response.currency

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.nk.currencyrates.model.response.currency.CurrenciesData.Companion.TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
@JsonClass(generateAdapter = true)
data class CurrenciesData(
    @PrimaryKey(autoGenerate = true)
    var primaryKeyId: Int?,
    @Json(name = "success") val name: Boolean?,
    @Json(name = "timestamp") val email: String?,
    @Json(name = "base") val cell: String?,
    @Json(name = "rates") var currenciesRates: Map<String, Float>?,
): Parcelable {
    companion object {
        const val TABLE_NAME = "currencies"
    }
}

/**
 * [RatesListJsonConverter] will be to convert Rates List to json and back for room database storage
 */
class RatesListJsonConverter {
    /**
     * Converts rates list of type [List<String>] to json [String]
     * @param rates of type [List<String>]
     * @return [String]
     */
    @TypeConverter
    fun fromRates(rates: Map<String, Float>?): String? {
        return Moshi.Builder().build().adapter<Any>(Types.newParameterizedType(MutableMap::class.java, String::class.java, Any::class.java)).toJson(rates)
    }

    /**
     * Converts ratesJson of type [String] to of type [List<String>]
     * @param ratesJson of type [String]
     * @return [List<String>]
     */
    @TypeConverter
    fun toRates(ratesJson: String?): Map<String, Float>? {
        return Moshi.Builder().build().adapter<Any>(Types.newParameterizedType(MutableMap::class.java, String::class.java, Any::class.java)).fromJson(ratesJson ?: "") as? Map<String, Float>?
    }
}












//
///**
// * [NameJsonConverter] will be to convert name to json and back for room database storage
// */
//class NameJsonConverter {
//    /**
//     * Converts name of type [Name] to json [String]
//     * @param name of type [Name]
//     * @return [String]
//     */
//    @TypeConverter
//    fun fromMenu(name: Name?): String? {
//        return Moshi.Builder().build().adapter(Name::class.java).toJson(name)
//    }
//
//    /**
//     * Converts nameJson of type [String] to name of type [Name]
//     * @param nameJson of type [String]
//     * @return [Name]
//     */
//    @TypeConverter
//    fun toMenu(nameJson: String?): Name? {
//        return Moshi.Builder().build().adapter(Name::class.java).fromJson(nameJson ?: "")
//    }
//}
//
//
///**
// * [LocationJsonConverter] will be to convert name to json and back for room database storage
// */
//class LocationJsonConverter {
//    /**
//     * Converts name of type [Location] to json [String]
//     * @param location of type [Location]
//     * @return [String]
//     */
//    @TypeConverter
//    fun fromLocation(location: Location?): String? {
//        return Moshi.Builder().build().adapter(Location::class.java).toJson(location)
//    }
//
//    /**
//     * Converts locationJson of type [String] to location of type [Location]
//     * @param locationJson of type [String]
//     * @return [Location]
//     */
//    @TypeConverter
//    fun toLocation(locationJson: String?): Location? {
//        return Moshi.Builder().build().adapter(Location::class.java).fromJson(locationJson ?: "")
//    }
//}
//
///**
// * [PictureJsonConverter] will be to convert name to json and back for room database storage
// */
//class PictureJsonConverter {
//    /**
//     * Converts name of type [Picture] to json [String]
//     * @param picture of type [Picture]
//     * @return [String]
//     */
//    @TypeConverter
//    fun fromPicture(picture: Picture?): String? {
//        return Moshi.Builder().build().adapter(Picture::class.java).toJson(picture)
//    }
//
//    /**
//     * Converts pictureJson of type [String] to picture of type [Picture]
//     * @param pictureJson of type [String]
//     * @return [Picture]
//     */
//    @TypeConverter
//    fun toLocation(pictureJson: String?): Picture? {
//        return Moshi.Builder().build().adapter(Picture::class.java).fromJson(pictureJson ?: "")
//    }
//}
//
//








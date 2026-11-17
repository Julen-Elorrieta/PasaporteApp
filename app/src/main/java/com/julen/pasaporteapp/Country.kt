package com.julen.pasaporteapp

data class Country(
    val name: String,
    val officialName: String,
    val alpha2: String,
    val alpha3: String,
    val numeric: String,
    val capital: String?, // Nullable por si algún territorio no tiene capital
    val region: String,
    val subregion: String,
    val population: Long,
    val area: Double,
    val flag: String,
    val flags: Flags,
    val currencies: List<Currency>,
    val languages: List<Language>,
    val callingCodes: List<String>,
    val timezones: List<String>,
    val borders: List<String>,
    val latlng: List<Double>
)

data class Flags(
    val svg: String,
    val png: String
)

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class Language(
    val code: String,
    val name: String
)

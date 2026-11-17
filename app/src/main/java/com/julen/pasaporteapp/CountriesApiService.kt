package com.julen.pasaporteapp

import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {

    // Obtener un país por su código ISO (ej. "ES", "US")
    @GET("alpha/{code}")
    suspend fun getCountryByCode(@Path("code") code: String): Country

    // Obtener todos los países (devuelve una lista)
    @GET("countries")
    suspend fun getAllCountries(): List<Country>
}

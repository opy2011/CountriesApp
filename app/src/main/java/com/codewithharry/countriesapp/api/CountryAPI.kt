package com.codewithharry.countriesapp.api

import retrofit2.Response
import retrofit2.http.GET

interface CountryAPI {
    @GET("/v3.1/region/asia/")
    suspend fun getRides(): Response<List<Country>>
}
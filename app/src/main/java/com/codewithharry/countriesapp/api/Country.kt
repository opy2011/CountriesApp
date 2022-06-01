package com.codewithharry.countriesapp.api

import com.codewithharry.countriesapp.pojos.Flags
import com.codewithharry.countriesapp.pojos.Name


data class Country(
    val name: Name?,
    val capital: ArrayList<String>?,
    val flags: Flags?,
    val region: String?,
    val subregion: String?,
    val population: Int?,
    val borders: List<String>?,
    val languages: Map<String,String>?
)

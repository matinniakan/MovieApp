package com.example.moviesapp.repository

import com.example.moviesapp.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:ApiServices) {
    suspend fun topMoviesList(id:Int) = api.topMoviesList(id)
    suspend fun genresList() = api.genresList()
    suspend fun lastMoviesList() = api.lastMoviesList()
}
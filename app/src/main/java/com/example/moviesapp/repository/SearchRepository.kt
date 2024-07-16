package com.example.moviesapp.repository

import androidx.datastore.preferences.protobuf.Api
import com.example.moviesapp.api.ApiServices
import javax.inject.Inject
import javax.inject.Named

class SearchRepository @Inject constructor(private val api:ApiServices) {
    suspend fun searchMovie(name: String) = api.searchMovie(name)
}
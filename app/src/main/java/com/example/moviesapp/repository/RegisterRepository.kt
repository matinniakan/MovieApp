package com.example.moviesapp.repository

import com.example.moviesapp.api.ApiServices
import com.example.moviesapp.models.register.BodyRegister
import retrofit2.http.Body
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api:ApiServices) {
    suspend fun registerUser( body: BodyRegister) = api.registerUser(body)
}
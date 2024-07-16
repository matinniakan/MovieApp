package com.example.moviesapp.api

import com.example.moviesapp.models.detail.ResponseDetail
import com.example.moviesapp.models.home.ResponseGenresList
import com.example.moviesapp.models.home.ResponseMoviesList
import com.example.moviesapp.models.register.BodyRegister
import com.example.moviesapp.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister) :Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun topMoviesList(@Path("genre_id") id:Int):Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList():Response<ResponseGenresList>

    @GET("movies")
    suspend fun lastMoviesList():Response<ResponseMoviesList>

    @GET("movies")
    suspend fun searchMovie(@Query("q") name:String):Response<ResponseMoviesList>

    @GET("movies/{movie_id}")
    suspend fun detailMovie(@Path("movie_id") id:Int):Response<ResponseDetail>
}

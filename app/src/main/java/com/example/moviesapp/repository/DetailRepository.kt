package com.example.moviesapp.repository

import com.example.moviesapp.api.ApiServices
import com.example.moviesapp.db.MoviesDao
import com.example.moviesapp.db.MoviesEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices,private val dao:MoviesDao) {

    //api
    suspend fun detailMovie(id:Int) = api.detailMovie(id)

    //database
    suspend fun insertMovie(entity: MoviesEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MoviesEntity) = dao.deleteMovie(entity)
    suspend fun existMovie(movieId:Int) = dao.existMovie(movieId)

}
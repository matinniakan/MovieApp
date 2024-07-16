package com.example.moviesapp.repository

import com.example.moviesapp.db.MoviesDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao:MoviesDao) {

     fun getAllFavoriteList() =dao.getAllFavoriteMovies()

}
package com.example.moviesapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.utils.Constants

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(entity: MoviesEntity)

    @Delete
    suspend fun deleteMovie(entity: MoviesEntity)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}" )
    fun getAllFavoriteMovies() :MutableList<MoviesEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.TABLE_NAME} WHERE Id = :movieId)")
    suspend fun existMovie(movieId:Int):Boolean

}
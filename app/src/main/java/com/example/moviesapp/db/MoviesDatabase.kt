package com.example.moviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase :RoomDatabase() {
    abstract fun moviesDao():MoviesDao
}
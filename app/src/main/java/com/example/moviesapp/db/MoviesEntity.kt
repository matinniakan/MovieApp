package com.example.moviesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class MoviesEntity(
    @PrimaryKey
    var id:Int = 0,
    var poster:String ="",
    var title:String ="",
    var rate:String ="",
    var country:String ="",
    var year:String =""
)

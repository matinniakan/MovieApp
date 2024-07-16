package com.example.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.db.MoviesEntity
import com.example.moviesapp.models.detail.ResponseDetail
import com.example.moviesapp.models.home.ResponseMoviesList
import com.example.moviesapp.repository.DetailRepository
import com.example.moviesapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository): ViewModel(){

    //api
    val detailMovie = MutableLiveData<ResponseDetail>()
    val loading = MutableLiveData<Boolean>()

    fun loadDetailMovie(id:Int) =viewModelScope.launch {
        loading.postValue(true)
        val response =repository.detailMovie(id)
        if (response.isSuccessful){
            detailMovie.postValue(response.body())
        }
        loading.postValue(false)
    }

    //database
    val isFavorite = MutableLiveData<Boolean>()
    suspend fun existMovie(id:Int) = withContext(viewModelScope.coroutineContext) { repository.existMovie(id)}

    fun favoriteMovie(id:Int,entity:MoviesEntity) = viewModelScope.launch {
        val exist =repository.existMovie(id)
        if (exist){
            isFavorite.postValue(false)
            repository.deleteMovie(entity)
        }else{
            isFavorite.postValue(true)
            repository.insertMovie(entity)
        }
    }
}
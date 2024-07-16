package com.example.moviesapp.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

fun View.showInVisible(isShown:Boolean){
    if(isShown){
        this.visibility =View.VISIBLE
    }else{
        this.visibility =View.INVISIBLE
    }
}

fun RecyclerView.initRecycler(layoutManager:RecyclerView.LayoutManager,adapter:RecyclerView.Adapter<*>){
    this.layoutManager =layoutManager
    this.adapter = adapter
}
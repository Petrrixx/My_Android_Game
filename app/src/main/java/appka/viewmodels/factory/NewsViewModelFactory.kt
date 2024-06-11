package com.example.towerdefense_seminar_bolecek_peter_5zyi24


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(private val newsItemDao: NewsItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





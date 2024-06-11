package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HubViewModelFactory(private val hubsItemDao: HubsItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HubViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HubViewModel(hubsItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GuidesViewModelFactory(private val guideDao: GuideDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuidesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GuidesViewModel(guideDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
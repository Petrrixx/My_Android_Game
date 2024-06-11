package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatsViewModel(private val statsItemDao: StatsItemDao) : ViewModel() {
    private val _statsItems = MutableStateFlow<List<StatsItem>>(emptyList())
    val statsItems: StateFlow<List<StatsItem>> get() = _statsItems

    init {
        refreshStatsItems()
    }

    fun refreshStatsItems() {
        viewModelScope.launch {
            _statsItems.value = statsItemDao.getAllStatsItems()
        }
    }
}


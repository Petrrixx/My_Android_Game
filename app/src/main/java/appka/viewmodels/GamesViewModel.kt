package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GamesViewModel(private val gameDao: GameDao) : ViewModel() {
    private val _gamesItems = MutableStateFlow<List<Game>>(emptyList())
    val gamesItems: StateFlow<List<Game>> get() = _gamesItems

    init {
        refreshGameItems()
    }

    fun refreshGameItems() {
        viewModelScope.launch {
            _gamesItems.value = gameDao.getAllGamesItems()
        }
    }
}



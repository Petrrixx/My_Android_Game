package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class HubViewModel(private val hubsItemDao: HubsItemDao) : ViewModel() {
    private val _hubItems = MutableStateFlow<List<HubsItem>>(emptyList())
    val hubItems: StateFlow<List<HubsItem>> get() = _hubItems

    init {
        refreshHubItems()
    }

    fun refreshHubItems() {
        viewModelScope.launch {
            _hubItems.value = hubsItemDao.getAllHubsItems()
        }
    }
}


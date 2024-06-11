package com.example.towerdefense_seminar_bolecek_peter_5zyi24


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsItemDao: NewsItemDao) : ViewModel() {
    private val _newsItems = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsItems: StateFlow<List<NewsItem>> get() = _newsItems

    init {
        viewModelScope.launch {
            _newsItems.value = newsItemDao.getAllNewsItems()
        }
    }

    fun refreshNewsItems() {
        viewModelScope.launch {
            _newsItems.value = newsItemDao.getAllNewsItems()
        }
    }
}






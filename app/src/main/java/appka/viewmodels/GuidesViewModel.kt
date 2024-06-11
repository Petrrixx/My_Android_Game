package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GuidesViewModel(private val guideDao: GuideDao) : ViewModel() {
    private val _guidesItems = MutableStateFlow<List<Guide>>(emptyList())
    val guidesItems: StateFlow<List<Guide>> get() = _guidesItems

    init {
        refreshGuideItems()
    }

    fun refreshGuideItems() {
        viewModelScope.launch {
            _guidesItems.value = guideDao.getAllGuidesItems()
        }
    }
}


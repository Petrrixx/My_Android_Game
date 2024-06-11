package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val appUserDao: AppUserDao) : ViewModel() {
    private val _user = MutableLiveData<AppUser?>()
    val user: LiveData<AppUser?> = _user

    fun getUser(username: String, password: String) {
        viewModelScope.launch {
            _user.value = appUserDao.getUser(username, password)
        }
    }

    fun getUserByNick(username: String, nickname: String) {
        viewModelScope.launch {
            _user.value = appUserDao.getUserByNickname(username, nickname)
        }
    }

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            _user.value = appUserDao.loadUserById(userId)
        }
    }
}





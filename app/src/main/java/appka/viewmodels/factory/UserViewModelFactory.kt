package appka.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.AppUserDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.UserViewModel


class UserViewModelFactory(private val userDao: AppUserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

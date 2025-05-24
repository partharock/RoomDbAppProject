package com.android.daoproject.stevdza

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var readAllData: LiveData<List<User>>
    var repository: UserRepository
    var injectedRepository: Repository

    init {
        val dao = UserDatabase.getInstance(application).getDao()
        readAllData = dao.getAllUsers()
        repository = UserRepository(dao)
        injectedRepository = Repository(dao)
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            injectedRepository.insertUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun searchUser(searchQuery: String): LiveData<List<User>>? {
        return repository.searchUser(searchQuery)
    }

}
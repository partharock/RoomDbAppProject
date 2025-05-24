package com.android.daoproject.stevdza

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData = userDao.getAllUsers()

    suspend fun addUser(user : User){
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    fun searchUser(searchQuery: String) : LiveData<List<User>>{
        return userDao.searchDatabase(searchQuery)
    }

}
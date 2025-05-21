package com.android.daoproject.stevdza

class UserRepository(private val userDao: UserDao) {

    val readAllData = userDao.getAllUsers()

    suspend fun addUser(user : User){
        userDao.insertUser(user)
    }

}
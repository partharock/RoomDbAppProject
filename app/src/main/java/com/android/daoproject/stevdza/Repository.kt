package com.android.daoproject.stevdza

import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: UserDao
) {
    suspend fun insertUser(user: User) = dao.insertUser(user)

    suspend fun updateUser(user: User) = dao.updateUser(user)

    suspend fun deleteUser(user: User) = dao.deleteUser(user)

    suspend fun getAllUsers() = dao.getAllUsers()

    suspend fun searchUser(searchQuery: String) = dao.searchDatabase(searchQuery)


}
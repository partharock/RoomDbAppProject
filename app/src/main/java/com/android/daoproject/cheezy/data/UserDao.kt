package com.android.daoproject.cheezy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insertContact(user: User)

    @Update
    suspend fun updateContact(user: User)

    @Delete
    suspend fun deleteContact(user: User)

    @Query("SELECT * FROM user")
    fun getContacts() : LiveData<List<User>>

}
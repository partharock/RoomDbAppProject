package com.android.daoproject.stevdza

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context)
    : UserDatabase =
        Room.databaseBuilder(context,
            UserDatabase::class.java,
            "user_database").build()

    @Singleton
    @Provides
    fun provideDao(database: UserDatabase) = database.getDao()

}
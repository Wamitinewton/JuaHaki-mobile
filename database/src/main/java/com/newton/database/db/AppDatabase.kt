package com.newton.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newton.database.dao.UserDao
import com.newton.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}
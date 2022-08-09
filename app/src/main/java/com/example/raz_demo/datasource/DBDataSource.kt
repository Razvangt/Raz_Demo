package com.example.raz_demo.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.raz_demo.model.User
import com.example.raz_demo.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DBDataSource : RoomDatabase() {
    abstract fun  userDao(): UserDao
}
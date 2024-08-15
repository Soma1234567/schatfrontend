package com.soma.mychatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(SingleMessage::class),(AlreadyUsers::class),(BlockUser::class)], version = 1)
abstract class MessageDatabase:RoomDatabase() {

    abstract fun getDao():MessageDao
}
package com.soma.mychatapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlockUser(
    @PrimaryKey
    val username:String
)

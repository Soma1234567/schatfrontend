package com.soma.mychatapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SingleMessage(
    val from:String,
    val to:String,
    val message: String,
    val time:String,
    val date:String,
    val millis:String,
    val status:String,
    val avatar:String,
    //val fromto:String,
    val whom:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
)

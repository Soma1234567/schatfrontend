package com.soma.mychatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: SingleMessage)

    @Query("select * from singlemessage where `whom`=:user")
    fun getChatByUser(user:String):Flow<List<SingleMessage>>

    @Insert
    suspend fun blockuser(user:BlockUser)

    @Query("select * from blockuser")
    fun getblocklist():Flow<List<BlockUser>>

    @Query("select * from blockuser")
    fun getblocklistWithoutFlow():List<BlockUser>

    @Query("delete from blockuser where username=:user")
    suspend fun unblockUser(user:String)

    @Query("update singlemessage set `status` = :status where `millis`=:millis")
    suspend fun updateMessageStatus(status:String,millis:String)

    @Query("update singlemessage set `status` = :newstatus where `to`= :username and `status` = :oldstatus")
    suspend fun updatemessagestatustoRead(oldstatus:String,username:String,newstatus:String)

    @Query("update singlemessage set `status` = :newstatus where `millis`= :millis")
    suspend fun updatemessageToSent(newstatus: String,millis: String)

    @Query("delete from singlemessage where millis=:millis")
    suspend fun deleteForMeMessage(millis:String)

    @Query("update singlemessage set `message` = :deleted, `status`=:deletedstatus where `millis`= :millis")
    suspend fun updateDeletedMessage(deleted:String,millis: String,deletedstatus:String)

    @Query("select status from singlemessage where `millis`=:millis")
    suspend fun getMessageStatus(millis: String):String

    @Query("delete from singlemessage where `from`=:me and `to`=:username or `from`=:username and `to`=:me" )
    suspend fun clearChat(me:String,username:String)


    //selecting last row from each and everu user
    @Query("SELECT * FROM singlemessage WHERE id IN ( SELECT MAX(id) FROM singlemessage GROUP BY `whom`)")
    fun getAlreadyChatUsers():Flow<List<SingleMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlreadyChatUser(user:AlreadyUsers)

    @Query("delete from alreadyusers where `username`=:username")
    suspend fun deleteAlreadyChatUser(username:String)

    @Query("select * from alreadyusers")
    fun getAllChatUsers():Flow<List<AlreadyUsers>>
}

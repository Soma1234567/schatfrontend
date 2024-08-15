package com.soma.mychatapp.data.remote

import com.soma.mychatapp.data.remote.requests.CreateUser
import com.soma.mychatapp.data.remote.requests.DeleteMessage
import com.soma.mychatapp.data.remote.requests.LoginUser
import com.soma.mychatapp.data.remote.requests.MessageDto
import com.soma.mychatapp.data.remote.requests.MessageRecieved
import com.soma.mychatapp.data.remote.requests.ReadMessage
import com.soma.mychatapp.data.remote.requests.SearchUser
import com.soma.mychatapp.data.remote.requests.UpdateAvatar
import com.soma.mychatapp.data.remote.responces.CreateUserResponce
import com.soma.mychatapp.data.remote.responces.LoginUserResponce
import com.soma.mychatapp.data.remote.responces.MessageResponce
import com.soma.mychatapp.data.remote.responces.UpdateAvatarResponce
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @POST("/createuser")
    suspend fun createUser(
        @Body user: CreateUser
    ):CreateUserResponce

    @POST("/loginuser")
    suspend fun loginUser(
        @Body user:LoginUser
    ):LoginUserResponce

    @GET("/fetchusers")
    suspend fun fetchUsers(
        @Query("query") query: String
    ):List<SearchUser>

    @POST("/sendmessage")
    suspend fun sendMessage(
        @Body message:MessageDto
    ):MessageResponce

    @GET("/updatetoken")
    suspend fun updateToken(
        @Query("username") username:String,
        @Query("token") token:String
    )

    @POST("/messagerecived")
    suspend fun messageRecieved(
        @Body messageRecieved: MessageRecieved
    )

    @POST("/readmessages")
    suspend fun updateToReadMessages(
        @Body readMessage: ReadMessage
    )

    @POST("/deletemessage")
    suspend fun deleteMessageForEveryOne(
        @Body deleteMessage: DeleteMessage
    )

    @POST("/unsendmessage")
    suspend fun unsendMessage(
        @Body unsendMessage:DeleteMessage
    )

    @POST("/updateavatar")
    suspend fun updateAvatar(
        @Body updateAvatar: UpdateAvatar
    ):UpdateAvatarResponce

    @GET("/")
    suspend fun connect()
}
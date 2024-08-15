package com.soma.mychatapp.di

import androidx.room.Room
import com.soma.mychatapp.MySignUpViewModel
import com.soma.mychatapp.MySplashViewModel
import com.soma.mychatapp.data.local.MessageDatabase
import com.soma.mychatapp.data.remote.ChatApi
import com.soma.mychatapp.data.repository.DataStoreOperationsImpl
import com.soma.mychatapp.data.repository.Repository
import com.soma.mychatapp.domain.repository.DataStoreOperations
import com.soma.mychatapp.domain.use_cases.Usecases
import com.soma.mychatapp.domain.use_cases.block_user.BlockUserUseCase
import com.soma.mychatapp.domain.use_cases.clear_chat.ClearChatUseCase
import com.soma.mychatapp.domain.use_cases.connect.ConnectUseCase
import com.soma.mychatapp.domain.use_cases.create_user.CreateUserUsecase
import com.soma.mychatapp.domain.use_cases.delete_alraedy_user.DeleteALreadyChatUserUseCase
import com.soma.mychatapp.domain.use_cases.delete_for_everyone.DeleteForEveryOneUseCase
import com.soma.mychatapp.domain.use_cases.delete_for_me_message.DeleteForMeUseCase
import com.soma.mychatapp.domain.use_cases.fetch_user.FetchUsersUseCase
import com.soma.mychatapp.domain.use_cases.get_already_chat_users.GetAlreadyChatUsersUseCase
import com.soma.mychatapp.domain.use_cases.get_chat_users.GetChatUsersUseCase
import com.soma.mychatapp.domain.use_cases.get_message_status.GetMessageStatusUseCase
import com.soma.mychatapp.domain.use_cases.insert_message.InsertMessageUseCase
import com.soma.mychatapp.domain.use_cases.login_user.LoginUserUseCase
import com.soma.mychatapp.domain.use_cases.read_avatar.ReadAvatarUseCase
import com.soma.mychatapp.domain.use_cases.read_block_list.ReadBlockListUseCase
import com.soma.mychatapp.domain.use_cases.read_messages.ReadMessagesUseCase
import com.soma.mychatapp.domain.use_cases.read_username.ReadUsernameUsecase
import com.soma.mychatapp.domain.use_cases.save_avatar.SaveAvatarUseCase
import com.soma.mychatapp.domain.use_cases.save_username.SaveUsernameUseCase
import com.soma.mychatapp.domain.use_cases.send_message.SendMessageUseCase
import com.soma.mychatapp.domain.use_cases.unblock_user.UnblockUserUserCase
import com.soma.mychatapp.domain.use_cases.unsend_message.UnsendMessageUseCase
import com.soma.mychatapp.domain.use_cases.update_avatar.UpdateAvatarUseCase
import com.soma.mychatapp.domain.use_cases.update_deleted_message.UpdateDeletedMessageUseCase
import com.soma.mychatapp.domain.use_cases.update_message_to_sent.UpdateMessageToSentUseCase
import com.soma.mychatapp.domain.use_cases.update_read_messages.UpdateReadMessagesUseCase
import com.soma.mychatapp.domain.use_cases.update_token.UpdateTokenUseCase
import com.soma.mychatapp.presentation.chat_screen.ChatViewModel
import com.soma.mychatapp.presentation.home_screen.ChatsViewModel
import com.soma.mychatapp.presentation.home_screen.HomeViewModel
import com.soma.mychatapp.presentation.profile_screen.ProfileViewModel
import com.soma.mychatapp.presentation.serachuser_screen.SearchViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    single<ChatApi> {
        Retrofit.Builder()
            .baseUrl("https://mychatapp-emq4.onrender.com")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    single<MessageDatabase> {
        Room.databaseBuilder(
            get(),
            MessageDatabase::class.java,
            "message_database"
        ).build()
    }
    single<DataStoreOperations> {
        DataStoreOperationsImpl(get())
    }
    single<Repository> {
        Repository(get(),get(),get())
    }
    single<LoginUserUseCase> {
        LoginUserUseCase(get())
    }
    single<ReadUsernameUsecase> {
        ReadUsernameUsecase(get())
    }
    single<CreateUserUsecase> {
        CreateUserUsecase(get())
    }

    single<SaveUsernameUseCase> {
        SaveUsernameUseCase(get())
    }
    single<FetchUsersUseCase> {
        FetchUsersUseCase(get())
    }

    single<SendMessageUseCase> {
        SendMessageUseCase(get())
    }
    single<UpdateTokenUseCase> {
        UpdateTokenUseCase(get())
    }
    single<InsertMessageUseCase> {
        InsertMessageUseCase(get())
    }
    single<ReadMessagesUseCase> {
        ReadMessagesUseCase(get())
    }
    single<BlockUserUseCase> {
        BlockUserUseCase(get())
    }

    single<ReadBlockListUseCase> {
        ReadBlockListUseCase(get())
    }
    single<UnblockUserUserCase> {
        UnblockUserUserCase(get())
    }
    single<UpdateReadMessagesUseCase> {
        UpdateReadMessagesUseCase(get())
    }
    single<UpdateMessageToSentUseCase> {
        UpdateMessageToSentUseCase(get())
    }
    single<DeleteForMeUseCase> {
        DeleteForMeUseCase(get())
    }
    single<DeleteForEveryOneUseCase> {
        DeleteForEveryOneUseCase(get())
    }
    single<UpdateDeletedMessageUseCase> {
        UpdateDeletedMessageUseCase(get())
    }
    single<UnsendMessageUseCase> {
        UnsendMessageUseCase(get())
    }
    single<GetMessageStatusUseCase> {
        GetMessageStatusUseCase(get())
    }
    single<ClearChatUseCase> {
        ClearChatUseCase(get())
    }
    single<GetAlreadyChatUsersUseCase> {
        GetAlreadyChatUsersUseCase(get())
    }

    single<DeleteALreadyChatUserUseCase> {
        DeleteALreadyChatUserUseCase(get())
    }
    single<SaveAvatarUseCase> {
        SaveAvatarUseCase(get())
    }
    single<ReadAvatarUseCase> {
        ReadAvatarUseCase(get())
    }
    single<ConnectUseCase> {
        ConnectUseCase(get())
    }
    single<Usecases> {
        Usecases(get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get(),get())
    }
    single<GetChatUsersUseCase> {
        GetChatUsersUseCase(get())
    }
    single<UpdateAvatarUseCase> {
        UpdateAvatarUseCase(get())
    }
    viewModel<SearchViewModel>{
        SearchViewModel(get())
    }
    viewModel<ChatViewModel>{
        ChatViewModel(get())
    }
    viewModel<ChatsViewModel>{
        ChatsViewModel(get())
    }
    viewModel<HomeViewModel>{
        HomeViewModel(get())
    }
    viewModel<ProfileViewModel>{
        ProfileViewModel(get())
    }
    viewModel<MySplashViewModel>{
        MySplashViewModel(get())
    }
    viewModel<MySignUpViewModel>{
        MySignUpViewModel(get())
    }
}
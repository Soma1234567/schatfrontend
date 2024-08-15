package com.soma.mychatapp.domain.use_cases

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

data class Usecases (
    val readUsernameUsecase: ReadUsernameUsecase,
    val createUserUsecase: CreateUserUsecase,
    val saveUsernameUseCase: SaveUsernameUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val fetchUsersUseCase: FetchUsersUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val updateTokenUseCase: UpdateTokenUseCase,
    val insertMessageUseCase: InsertMessageUseCase,
    val readMessagesUseCase: ReadMessagesUseCase,
    val blockUserUseCase: BlockUserUseCase,
    val readBlockListUseCase: ReadBlockListUseCase,
    val unblockUserUserCase: UnblockUserUserCase,
    val updateReadMessagesUseCase: UpdateReadMessagesUseCase,
    val updateMessageToSentUseCase: UpdateMessageToSentUseCase,
    val deleteForMeUseCase: DeleteForMeUseCase,
    val deleteForEveryOneUseCase: DeleteForEveryOneUseCase,
    val updateDeletedMessageUseCase: UpdateDeletedMessageUseCase,
    val unsendMessageUseCase: UnsendMessageUseCase,
    val getMessageStatusUseCase: GetMessageStatusUseCase,
    val clearChatUseCase: ClearChatUseCase,
    val getAlreadyChatUsersUseCase: GetAlreadyChatUsersUseCase,
    val deleteALreadyChatUserUseCase: DeleteALreadyChatUserUseCase,
    val getChatUsersUseCase: GetChatUsersUseCase,
    val updateAvatarUseCase: UpdateAvatarUseCase,
    val saveAvatarUseCase: SaveAvatarUseCase,
    val readAvatarUseCase: ReadAvatarUseCase,
    val connectUseCase: ConnectUseCase
)
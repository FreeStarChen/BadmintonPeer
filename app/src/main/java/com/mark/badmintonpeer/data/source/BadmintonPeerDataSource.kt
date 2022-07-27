package com.mark.badmintonpeer.data.source

import androidx.lifecycle.MutableLiveData
import com.mark.badmintonpeer.data.*
import java.util.*

/**
 * Main entry point for accessing Publisher sources.
 */
interface BadmintonPeerDataSource {

    suspend fun login(id: String): Result<User>

    suspend fun getGroups(type: String): Result<List<Group>>

    suspend fun addGroup(group: Group) : Result<Boolean>

    suspend fun deleteGroup(id: String) : Result<Group>

    suspend fun addGroupMember(groupId: String, userId: String) : Result<Boolean>

    suspend fun subtractNeedPeopleNumber(groupId: String, needPeopleNumber: Int) : Result<Boolean>

    suspend fun getAlmostFullGroups(): Result<List<Group>>

    suspend fun getNews(): Result<List<News>>

    suspend fun getGroupChatroom(groupId: String): Result<Chatroom>

    suspend fun addChatroom(chatroom: Chatroom): Result<Boolean>

    suspend fun getAllChatroom() : Result<List<Chatroom>>

    suspend fun getTypeChatroom(type: String) : Result<List<Chatroom>>

    suspend fun getChats(chatroomId: String): Result<List<Chat>>

    suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean>

    suspend fun addChatroomMessageAndTime(chatroomId: String, message: String): Result<Boolean>

    fun getLiveChats(chatroomId: String) : MutableLiveData<List<Chat>>

    suspend fun getSearchCityGroup(city: String, type: String) : Result<List<Group>>

    suspend fun getFilterGroup(filter: Filter, type: String) : Result<List<Group>>

    suspend fun getComments(id: String) : Result<List<Comment>>

    suspend fun addComment() : Result<Comment>

    suspend fun getInvitation(id: String) : Result<List<Invitation>>

    suspend fun addInvitation() : Result<Invitation>

    suspend fun deleteInvitation(id: String) : Result<Invitation>

    suspend fun getUser(id: String) : Result<User>

    suspend fun addUser(user: User) : Result<Boolean>

    suspend fun getOwner(ownerId: String): Result<User>

    suspend fun getJoinGroup(userId: String): Result<List<Group>>

    suspend fun getRecordOfCreatedGroup(type: String, ownerId: String): Result<List<Group>>

    suspend fun getRecordOfJoinGroup(type: String, userId: String): Result<List<Group>>

}
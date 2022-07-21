package com.mark.badmintonpeer.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mark.badmintonpeer.data.*
import com.mark.badmintonpeer.data.source.BadmintonPeerDataSource
import java.util.*

/**
 * Concrete implementation of a Badminton Peer source as a db.
 */
class BadmintonPeerLocalDataSource(val context: Context) : BadmintonPeerDataSource {
    override suspend fun login(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(type: String): Result<List<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun addGroup(group: Group): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(id: String): Result<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun addGroupMember(groupId: String, userId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun subtractNeedPeopleNumber(
        groupId: String,
        needPeopleNumber: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAlmostFullGroups(): Result<List<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNews(): Result<List<News>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupChatroom(groupId: String): Result<Chatroom> {
        TODO("Not yet implemented")
    }

    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChatroom(): Result<List<Chatroom>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChats(chatroomId: String): Result<List<Chat>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addChatroomMessageAndTime(
        chatroomId: String,
        message: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }


    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchCityGroup(city: String, type: String): Result<List<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilterGroup(filter: Filter, type: String): Result<List<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun getComments(id: String): Result<List<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun addComment(): Result<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun getInvitation(id: String): Result<List<Invitation>> {
        TODO("Not yet implemented")
    }

    override suspend fun addInvitation(): Result<Invitation> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteInvitation(id: String): Result<Invitation> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getOwner(ownerId: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getJoinGroup(userId: String): Result<List<Group>> {
        TODO("Not yet implemented")
    }
}
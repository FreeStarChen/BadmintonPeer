package com.mark.badmintonpeer.data.source

import androidx.lifecycle.MutableLiveData
import com.mark.badmintonpeer.data.*
import java.util.*

/**
 * Concrete implementation to load Publisher sources.
 */
class DefaultBadmintonPeerRepository(
    private val remoteDataSource: BadmintonPeerDataSource,
    private val localDataSource: BadmintonPeerDataSource
) : BadmintonPeerRepository {
    override suspend fun login(id: String): Result<User> {
        return localDataSource.login(id)
    }

    override suspend fun getGroups(type: String): Result<List<Group>> {
        return remoteDataSource.getGroups(type)
    }

    override suspend fun addGroup(group: Group): Result<Boolean> {
        return remoteDataSource.addGroup(group)
    }

    override suspend fun deleteGroup(id: String): Result<Group> {
        return remoteDataSource.deleteGroup(id)
    }

    override suspend fun addGroupMember(groupId: String, userId: String): Result<Boolean> {
        return remoteDataSource.addGroupMember(groupId, userId)
    }

    override suspend fun subtractNeedPeopleNumber(
        groupId: String,
        needPeopleNumber: Int
    ): Result<Boolean> {
        return remoteDataSource.subtractNeedPeopleNumber(groupId, needPeopleNumber)
    }

    override suspend fun getAlmostFullGroups(): Result<List<Group>> {
        return remoteDataSource.getAlmostFullGroups()
    }

    override suspend fun getNews(): Result<List<News>> {
        return remoteDataSource.getNews()
    }


    override suspend fun getGroupChatroom(groupId: String): Result<Chatroom> {
        return remoteDataSource.getGroupChatroom(groupId)
    }

    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean> {
        return remoteDataSource.addChatroom(chatroom)
    }

    override suspend fun getAllChatroom(): Result<List<Chatroom>> {
        return remoteDataSource.getAllChatroom()
    }

    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> {
        return remoteDataSource.getTypeChatroom(type)
    }

    override suspend fun getChats(chatroomId: String): Result<List<Chat>> {
        return remoteDataSource.getChats(chatroomId)
    }

    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> {
        return remoteDataSource.sendChat(chatroomId, chat)
    }

    override suspend fun addChatroomMessageAndTime(chatroomId: String, message: String): Result<Boolean> {
        return remoteDataSource.addChatroomMessageAndTime(chatroomId, message)
    }


    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>> {
        return remoteDataSource.getLiveChats(chatroomId)
    }

    override suspend fun getSearchCityGroup(city: String, type: String): Result<List<Group>> {
        return remoteDataSource.getSearchCityGroup(city,type)
    }

    override suspend fun getFilterGroup(filter: Filter, type: String): Result<List<Group>> {
        return remoteDataSource.getFilterGroup(filter,type)
    }

    override suspend fun getComments(id: String): Result<List<Comment>> {
        return remoteDataSource.getComments(id)
    }

    override suspend fun addComment(): Result<Comment> {
        return remoteDataSource.addComment()
    }

    override suspend fun getInvitation(id: String): Result<List<Invitation>> {
        return remoteDataSource.getInvitation(id)
    }

    override suspend fun addInvitation(): Result<Invitation> {
        return remoteDataSource.addInvitation()
    }

    override suspend fun deleteInvitation(id: String): Result<Invitation> {
        return remoteDataSource.deleteInvitation(id)
    }

    override suspend fun getUser(id: String): Result<User> {
        return remoteDataSource.getUser(id)
    }

    override suspend fun addUser(user: User): Result<Boolean> {
        return remoteDataSource.addUser(user)
    }

    override suspend fun getOwner(ownerId: String): Result<User> {
        return remoteDataSource.getOwner(ownerId)
    }

    override suspend fun getJoinGroup(userId: String): Result<List<Group>> {
        return remoteDataSource.getJoinGroup(userId)
    }
}
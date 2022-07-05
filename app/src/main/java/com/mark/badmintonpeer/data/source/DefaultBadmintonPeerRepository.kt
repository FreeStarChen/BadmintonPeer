package com.mark.badmintonpeer.data.source

import androidx.lifecycle.MutableLiveData
import com.mark.badmintonpeer.data.*

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

    override suspend fun subtractNeedPeopleNumber(groupId: String, needPeopleNumber: Int): Result<Boolean> {
        return remoteDataSource.subtractNeedPeopleNumber(groupId, needPeopleNumber)
    }

    override suspend fun getAllChatroom(): Result<List<Chatroom>> {
        return remoteDataSource.getAllChatroom()
    }

    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> {
        return remoteDataSource.getTypeChatroom(type)
    }


    override fun getLiveChats(id: String): MutableLiveData<List<Chat>> {
        return remoteDataSource.getLiveChats(id)
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
}
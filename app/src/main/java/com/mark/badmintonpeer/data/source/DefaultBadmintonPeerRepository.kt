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

    override suspend fun getGroups(): Result<List<Group>> {
        return remoteDataSource.getGroups()
    }

    override suspend fun addGroup(): Result<Group> {
        return remoteDataSource.addGroup()
    }

    override suspend fun deleteGroup(id: String): Result<Group> {
        return remoteDataSource.deleteGroup(id)
    }

    override suspend fun getChatroom(id: String): Result<List<Chatroom>> {
        return remoteDataSource.getChatroom(id)
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
}
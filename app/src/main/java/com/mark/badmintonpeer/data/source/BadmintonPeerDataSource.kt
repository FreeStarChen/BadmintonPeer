package com.mark.badmintonpeer.data.source

import androidx.lifecycle.MutableLiveData
import com.mark.badmintonpeer.data.*

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

    suspend fun getChatroom(id: String) : Result<List<Chatroom>>

    fun getLiveChats(id: String) : MutableLiveData<List<Chat>>

    suspend fun getComments(id: String) : Result<List<Comment>>

    suspend fun addComment() : Result<Comment>

    suspend fun getInvitation(id: String) : Result<List<Invitation>>

    suspend fun addInvitation() : Result<Invitation>

    suspend fun deleteInvitation(id: String) : Result<Invitation>

}
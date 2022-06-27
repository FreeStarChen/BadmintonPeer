package com.mark.badmintonpeer.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.*
import com.mark.badmintonpeer.data.source.BadmintonPeerDataSource
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Implementation of the Publisher source that from network.
 */
object BadmintonPeerRemoteDataSource : BadmintonPeerDataSource {

    private const val PATH_GROUPS = "group"
    private const val KEY_START_TIME = "startTime"
    private const val KEY_CLASSIFICATION = "classification"

    override suspend fun login(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(type:String): Result<List<Group>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GROUPS)
            .whereEqualTo(KEY_CLASSIFICATION,type)
//            .orderBy(KEY_START_TIME,Query.Direction.ASCENDING)
//            .limit(10)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Group>()
                    for (document in task.result) {
                        Timber.d(document.id + " => " + document.data)

                        val group = document.toObject(Group::class.java)
                        list.add(group)
                    }
                    continuation.resume(Result.Success(list))
                }else {
                    task.exception?.let {
                        Timber.e("Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addGroup(group: Group): Result<Boolean> = suspendCoroutine { continuation ->
        val groups = FirebaseFirestore.getInstance().collection(PATH_GROUPS)
        val document = groups.document()
        Timber.d("addGroup")
        group.id = document.id
        Timber.d("group.id=${group}")
        //之後要加ownerId
        document
            .set(group)
            .addOnCompleteListener { task ->
                Timber.i("Add group Complete")
                if (task.isSuccessful) {
                    Timber.d("Add group=$group")
                    continuation.resume(Result.Success(true))
                }else {
                    task.exception?.let {
                        Timber.e("Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun deleteGroup(id: String): Result<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun getChatroom(id: String): Result<List<Chatroom>> {
        TODO("Not yet implemented")
    }

    override fun getLiveChats(id: String): MutableLiveData<List<Chat>> {
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
}
package com.mark.badmintonpeer.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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

    private const val PATH_GROUPS = "groups"
    private const val KEY_START_TIME = "startTime"
    private const val KEY_CLASSIFICATION = "classification"
    private const val KEY_MEMBER = "member"
    private const val KEY_NEED_PEOPLE_NUMBER = "needPeopleNumber"
    private const val PATH_CHATROOM = "chatroom"
    private const val KEY_TYPE = "type"
    private const val KEY_LAST_TALK_TIME = "lastTalkTime"
    private const val PATH_USERS = "users"
    private const val KEY_ID = "id"

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

    override suspend fun addGroupMember(groupId: String, userId: String): Result<Boolean> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GROUPS)
            .document(groupId)
            .update(KEY_MEMBER,FieldValue.arrayUnion(userId))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Add group member complete")
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

    override suspend fun subtractNeedPeopleNumber(groupId: String, needPeopleNumber: Int): Result<Boolean> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GROUPS)
            .document(groupId)
            .update(KEY_NEED_PEOPLE_NUMBER,needPeopleNumber-1)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("subtract need people number complete")
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

    override suspend fun getAllChatroom(): Result<List<Chatroom>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
//            .whereEqualTo(KEY_CLASSIFICATION,type)
//            .orderBy(KEY_START_TIME,Query.Direction.ASCENDING)
//            .limit(10)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Chatroom>()
                    for (document in task.result) {
                        Timber.d(document.id + " => " + document.data)

                        val chatroom = document.toObject(Chatroom::class.java)
                        list.add(chatroom)
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

    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .whereEqualTo(KEY_TYPE,type)
//            .orderBy(KEY_LAST_TALK_TIME,Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Chatroom>()
                    for (document in task.result) {
                        Timber.d(document.id + " => " + document.data)

                        val chatroom = document.toObject(Chatroom::class.java)
                        list.add(chatroom)
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

    override suspend fun checkUser(id: String): Result<User> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USERS)
            .whereEqualTo(KEY_ID,id)
            .get()
            .addOnSuccessListener {
                Timber.d("checkUser addOnSuccessListener")
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("task=${task.result}")
                    if (task.result.documents.isEmpty()){
                        Timber.d("empty")
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }else {
                        for (document in task.result) {
                            Timber.d("document=${document}")
                            val user = document.toObject(User::class.java)
                            Timber.d("user=${user}")
                            continuation.resume(Result.Success(user))
                        }
                    }
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

    override suspend fun addUser(user: User): Result<Boolean> = suspendCoroutine { continuation ->
        val groups = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val document = groups.document(user.id)

        document
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Add user=$user")
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

}
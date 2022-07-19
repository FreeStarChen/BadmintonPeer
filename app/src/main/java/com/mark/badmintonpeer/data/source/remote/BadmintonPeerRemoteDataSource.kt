package com.mark.badmintonpeer.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.*
import com.mark.badmintonpeer.data.source.BadmintonPeerDataSource
import com.mark.badmintonpeer.login.UserManager
import timber.log.Timber
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Implementation of the Publisher source that from network.
 */
object BadmintonPeerRemoteDataSource : BadmintonPeerDataSource {

    private const val PATH_GROUPS = "groups"
    private const val KEY_DATE = "date"
    private const val KEY_CLASSIFICATION = "classification"
    private const val KEY_MEMBER = "member"
    private const val KEY_NEED_PEOPLE_NUMBER = "needPeopleNumber"
    private const val PATH_CHATROOM = "chatroom"
    private const val KEY_TYPE = "type"
    private const val KEY_LAST_TALK_TIME = "lastTalkTime"
    private const val PATH_USERS = "users"
    private const val KEY_ID = "id"
    private const val KEY_GROUP_ID = "groupId"
    private const val PATH_CHATS = "chats"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val KEY_LAST_TALK_MESSAGE = "lastTalkMessage"
    private const val KEY_ADDRESS = "address"
    private const val PATH_NEWS = "news"
    private const val KEY_POST_TIME = "postTime"

    override suspend fun login(id: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(type: String): Result<List<Group>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_GROUPS)
                .orderBy(KEY_DATE, Query.Direction.ASCENDING)
                .whereEqualTo(KEY_CLASSIFICATION, type)
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
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addGroup(group: Group): Result<Boolean> =
        suspendCoroutine { continuation ->
            val groups = FirebaseFirestore.getInstance().collection(PATH_GROUPS)
            val document = groups.document()
            Timber.d("addGroup")
            group.id = document.id
            Timber.d("group.id=${group}")
            group.ownerId = UserManager.userId.toString()
            document
                .set(group)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Add group=$group")
                        continuation.resume(Result.Success(true))
                    } else {
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

    override suspend fun addGroupMember(groupId: String, userId: String): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_GROUPS)
                .document(groupId)
                .update(KEY_MEMBER, FieldValue.arrayUnion(userId))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Add group member complete")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun subtractNeedPeopleNumber(
        groupId: String,
        needPeopleNumber: Int
    ): Result<Boolean> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_GROUPS)
            .document(groupId)
            .update(KEY_NEED_PEOPLE_NUMBER, needPeopleNumber - 1)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("subtract need people number complete")
                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {
                        Timber.e("Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getAlmostFullGroups(): Result<List<Group>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_GROUPS)
//                .orderBy(KEY_DATE, Query.Direction.ASCENDING)
                .whereLessThanOrEqualTo(KEY_NEED_PEOPLE_NUMBER, 2)
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
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getNews(): Result<List<News>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_NEWS)
                .orderBy(KEY_POST_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<News>()
                        for (document in task.result) {
                            Timber.d(document.id + " => " + document.data)

                            val news = document.toObject(News::class.java)
                            list.add(news)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getGroupChatroom(groupId: String): Result<Chatroom> =
        suspendCoroutine { continuation ->
            UserManager.userId?.let { id ->
                Timber.d("UserManager.userId=$id")
                Timber.d("groupId=$groupId")
                FirebaseFirestore.getInstance()
                    .collection(PATH_CHATROOM)
                    .whereEqualTo(KEY_GROUP_ID, groupId)
                    .whereArrayContains(KEY_MEMBER, id)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result.isEmpty) {
                                Timber.d("getGroupChatroom task.result.documents =${task.result.documents}")
                                continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                            } else {
                                for (document in task.result) {
                                    Timber.d("document=${document}")
                                    val chatroom = document.toObject(Chatroom::class.java)
                                    Timber.d("user=${chatroom}")
                                    continuation.resume(Result.Success(chatroom))
                                }
                            }
                        } else {
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

    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean> =
        suspendCoroutine { continuation ->
            val chatroomCollection = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val document = chatroomCollection.document()

            chatroom.id = document.id

            document
                .set(chatroom)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Add chatroom=$chatroom")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }


    override suspend fun getAllChatroom(): Result<List<Chatroom>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .orderBy(KEY_LAST_TALK_TIME, Query.Direction.DESCENDING)
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
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .whereEqualTo(KEY_TYPE, type)
                .orderBy(KEY_LAST_TALK_TIME, Query.Direction.DESCENDING)
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
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getChats(chatroomId: String): Result<List<Chat>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(chatroomId)
                .collection(PATH_CHATS)
                .orderBy(KEY_CREATED_TIME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    Timber.d("getChats addOnCompleteListener")
                    if (task.isSuccessful) {
                        val list = mutableListOf<Chat>()
                        for (document in task.result) {
                            Timber.d(document.id + " => " + document.data)

                            val chat = document.toObject(Chat::class.java)
                            list.add(chat)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> =
        suspendCoroutine { continuation ->
            val chatroomCollection = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val chatroomDocument = chatroomCollection.document(chatroomId)
            val chatCollection = chatroomDocument.collection(PATH_CHATS)
            val chatDocument = chatCollection.document()

            chat.id = chatDocument.id
            chat.createdTime = Date(System.currentTimeMillis())

            chatDocument
                .set(chat)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Add chat=$chat")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun addChatroomMessageAndTime(
        chatroomId: String,
        message: String
    ): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(chatroomId)
                .update(
                    mapOf(
                        KEY_LAST_TALK_MESSAGE to message,
                        KEY_LAST_TALK_TIME to Date(System.currentTimeMillis())
                    )
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Add chatroom message and time complete")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>> {
        val liveData = MutableLiveData<List<Chat>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .document(chatroomId)
            .collection(PATH_CHATS)
            .orderBy(KEY_CREATED_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapsot, exception ->
                Timber.i("addSnapshotListener detect")

                exception?.let {
                    Timber.w("Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Chat>()
                for (document in snapsot!!) {
                    Timber.d(document.id + " => " + document.data)

                    val chat = document.toObject(Chat::class.java)
                    list.add(chat)
                }

                liveData.value = list
                Timber.d("liveData.value = ${liveData.value}")
            }
        return liveData
    }

    override suspend fun getSearchCityGroup(city: String, type: String): Result<List<Group>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_GROUPS)
                .whereEqualTo(KEY_CLASSIFICATION, type)
                .orderBy(KEY_DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Group>()
                        for (document in task.result) {
                            Timber.d(document.id + " => " + document.data)

                            val group = document.toObject(Group::class.java)
                            if (group.address.contains(city)) {
                                list.add(group)
                            }
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
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

    override suspend fun getUser(id: String): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USERS)
            .whereEqualTo(KEY_ID, id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("getUser task=${task.result}")
                    if (task.result.documents.isEmpty()) {
                        Timber.d("getUser task.result.documents is empty")
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    } else {
                        for (document in task.result) {
                            Timber.d("document=${document}")
                            val user = document.toObject(User::class.java)
                            Timber.d("user=${user}")
                            continuation.resume(Result.Success(user))
                        }
                    }
                } else {
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
                } else {
                    task.exception?.let {
                        Timber.e("Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getOwner(ownerId: String): Result<User> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_USERS)
                .whereEqualTo(KEY_ID, ownerId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("getOwner task=${task.result}")
                        if (task.result.documents.isEmpty()) {
                            Timber.d("getOwner task.result.documents is empty")
                            continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                        } else {
                            for (document in task.result) {
                                Timber.d("document=${document}")
                                val owner = document.toObject(User::class.java)
                                Timber.d("owner=${owner}")
                                continuation.resume(Result.Success(owner))
                            }
                        }
                    } else {
                        task.exception?.let {
                            Timber.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getJoinGroup(userId: String): Result<List<Group>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_GROUPS)
                .whereArrayContains(KEY_MEMBER, userId)
                .orderBy(KEY_DATE, Query.Direction.ASCENDING)
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
                    } else {
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
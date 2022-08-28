package com.mark.badmintonpeer.groupdetail

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.User
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.login.UserManager
import com.mark.badmintonpeer.network.LoadApiStatus
import com.mark.badmintonpeer.util.TimeCalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class GroupDetailViewModel(val argument: Group, private val repository: BadmintonPeerRepository) :
    ViewModel() {

    private val _group = MutableLiveData<Group>().apply {
        value = argument
    }
    val group: LiveData<Group>
        get() = _group

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    // it for image circles design
    private val _snapPosition = MutableLiveData<Int>()
    val snapPosition: LiveData<Int>
        get() = _snapPosition

    private val _degree = MutableLiveData<List<String>>().apply {
        value = _group.value?.degree
    }
    val degree: LiveData<List<String>>
        get() = _degree

    private val _characteristic = MutableLiveData<List<String>>().apply {
        value = _group.value?.characteristic
    }
    val characteristic: LiveData<List<String>>
        get() = _characteristic

    // check user login status
    val isLoggedIn
        get() = UserManager.isLoggedIn

    val userId
        get() = UserManager.userId

    val decoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = 0
            } else {
                outRect.left =
                    MainApplication.instance.resources.getDimensionPixelSize(
                        R.dimen.space_detail_circle
                    )
            }
        }
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _owner = MutableLiveData<User>()
    val owner: LiveData<User>
        get() = _owner

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Timber.d("------------------------------------------")
        Timber.d("$this")
        Timber.d("------------------------------------------")

        getUserResult()
        getOwnerResult()
        checkIsMember()
    }

    fun addGroupMemberResult() {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            when (
                val result = group.value?.let { group ->
                    user.value?.let { user ->
                        repository.addGroupMember(
                            group.id,
                            user.id
                        )
                    }
                }
            ) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun subtractNeedPeopleNumberResult() {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            when (
                val result = group.value?.let {
                    repository.subtractNeedPeopleNumber(
                        it.id,
                        it.needPeopleNumber!!
                    )
                }
            ) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun getUserResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Timber.d("getUserResult is start")

            val result = UserManager.userId?.let { repository.getUser(it) }
            _user.value = when (result) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun getOwnerResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Timber.d("getUserResult is start")

            val result = group.value?.let { repository.getOwner(it.ownerId) }
            _owner.value = when (result) {

                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun checkIsMember(): Boolean {
        for (member in group.value?.member!!) {
            if (member == UserManager.userId) {
//                signUpStatus.value = R.string.already_sign_up.toString()
//                Timber.d("signUpStatus.value =${signUpStatus.value }")
                return true
            }
        }
//        signUpStatus.value = R.string.sign_up.toString()
//        Timber.d("signUpStatus.value =${signUpStatus.value }")
        return false
    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    /**
     * When the gallery scroll, at the same time circles design will switch.
     */
    fun onImageScrollChange(
        layoutManager: RecyclerView.LayoutManager?,
        linearSnapHelper: LinearSnapHelper
    ) {
        val snapView = linearSnapHelper.findSnapView(layoutManager)
        snapView?.let {
            layoutManager?.getPosition(snapView)?.let {
                if (it != snapPosition.value) {
                    _snapPosition.value = it
                }
            }
        }
    }

    val groupDate = _group.value?.date?.let { TimeCalculator.getDateAndWeek(it.time) }
    val groupStartTime = _group.value?.startTime?.let { TimeCalculator.getTime(it.time) }
    val groupEndTime = _group.value?.endTime?.let { TimeCalculator.getTime(it.time) }
    val groupPrice = "$${_group.value?.price}"
}

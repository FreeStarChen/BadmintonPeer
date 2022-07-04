package com.mark.badmintonpeer.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.User
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(val repository: BadmintonPeerRepository) : ViewModel() {

    private val _userFromFirebase = MutableLiveData<User>()

    val userFromFirebase: LiveData<User>
        get() = _userFromFirebase

    val userFromGooglelogin = MutableLiveData<User>()

    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<User>()

    val navigateToLoginSuccess: LiveData<User>
        get() = _navigateToLoginSuccess

    // Handle leave login
    private val _loginGoogle = MutableLiveData<Boolean>()

    val loginGoogle: LiveData<Boolean>
        get() = _loginGoogle

    // Handle leave login
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Timber.d("------------------------------------------")
        Timber.d("$this")
        Timber.d("------------------------------------------")
    }

    fun checkUserResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Timber.d("checkUserResult is start")

            val checkUserResult = userFromGooglelogin.value?.let { repository.checkUser(it.id) }
            Timber.d("checkUserResult=$checkUserResult")
            _userFromFirebase.value = when (checkUserResult) {

                is Result.Success -> {
                    Timber.d("_userFromFirebase=${_userFromFirebase.value}")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    checkUserResult.data

                }
                is Result.Fail -> {
                    _error.value = checkUserResult.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = checkUserResult.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            if (_userFromFirebase.value == null) {
                when (val addUserResult = repository.addUser(userFromGooglelogin.value!!)) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        UserManager.user.value = userFromGooglelogin.value
                        Timber.d("userFromGooglelogin.value=${userFromGooglelogin.value}")
                        leave()
                    }
                    is Result.Fail -> {
                        _error.value = addUserResult.error
                        _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _error.value = addUserResult.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR
                    }
                }

            } else {
                UserManager.user.value = _userFromFirebase.value
            }
            Timber.d("UserManager.user.value=${UserManager.user.value}")
        }
    }


    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun nothing() {}

}
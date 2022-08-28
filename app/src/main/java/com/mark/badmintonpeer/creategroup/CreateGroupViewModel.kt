package com.mark.badmintonpeer.creategroup

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateGroupViewModel(val repository: BadmintonPeerRepository) : ViewModel() {

    companion object {
        fun newInstance() = CreateGroupViewModel
    }

    private val _group = MutableLiveData<Group>().apply {
        value = Group()
    }
    val group: LiveData<Group>
        get() = _group

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _leave = MutableLiveData<Boolean?>()
    val leave: LiveData<Boolean?>
        get() = _leave

    private val _haveWaterDispenser = MutableLiveData<Boolean>()
    val haveWaterDispenser: LiveData<Boolean>
        get() = _haveWaterDispenser

    private val _haveAirCondition = MutableLiveData<Boolean>()
    val haveAirCondition: LiveData<Boolean>
        get() = _haveAirCondition

    private val _havePuGround = MutableLiveData<Boolean>()
    val havePuGround: LiveData<Boolean>
        get() = _havePuGround

    private val _haveSpotlight = MutableLiveData<Boolean>()
    val haveSpotlight: LiveData<Boolean>
        get() = _haveSpotlight

    private val _haveShower = MutableLiveData<Boolean>()
    val haveShower: LiveData<Boolean>
        get() = _haveShower

    private val _haveParking = MutableLiveData<Boolean>()
    val haveParking: LiveData<Boolean>
        get() = _haveParking

    private val _haveCutlery = MutableLiveData<Boolean>()
    val haveCutlery: LiveData<Boolean>
        get() = _haveCutlery

    private val _haveHairDryer = MutableLiveData<Boolean>()
    val haveHairDryer: LiveData<Boolean>
        get() = _haveHairDryer

    private val _degree1 = MutableLiveData<Boolean>()
    val degree1: LiveData<Boolean>
        get() = _degree1

    private val _degree2 = MutableLiveData<Boolean>()
    val degree2: LiveData<Boolean>
        get() = _degree2

    private val _degree3 = MutableLiveData<Boolean>()
    val degree3: LiveData<Boolean>
        get() = _degree3

    private val _degree4 = MutableLiveData<Boolean>()
    val degree4: LiveData<Boolean>
        get() = _degree4

    private val _degree5 = MutableLiveData<Boolean>()
    val degree5: LiveData<Boolean>
        get() = _degree5

    private val _degree6 = MutableLiveData<Boolean>()
    val degree6: LiveData<Boolean>
        get() = _degree6

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
    }

    fun addGroupResult() {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Timber.d("group.value=${group.value}")
            when (val result = repository.addGroup(group.value!!)) {
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

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    fun haveWaterDispenser() {
        _haveWaterDispenser.value = _haveWaterDispenser.value != true
    }
    fun haveAirCondition() {
        _haveAirCondition.value = _haveAirCondition.value != true
    }
    fun havePuGround() {
        _havePuGround.value = _havePuGround.value != true
    }

    fun haveSpotlight() {
        _haveSpotlight.value = _haveSpotlight.value != true
    }

    fun haveShower() {
        _haveShower.value = _haveShower.value != true
    }

    fun haveParking() {
        _haveParking.value = _haveParking.value != true
    }

    fun haveCutlery() {
        _haveCutlery.value = _haveCutlery.value != true
    }

    fun haveHairDryer() {
        _haveHairDryer.value = _haveHairDryer.value != true
    }

    fun degree1() {
        _degree1.value = _degree1.value != true
    }

    fun degree2() {
        _degree2.value = _degree2.value != true
    }

    fun degree3() {
        _degree3.value = _degree3.value != true
    }

    fun degree4() {
        _degree4.value = _degree4.value != true
    }

    fun degree5() {
        _degree5.value = _degree5.value != true
    }

    fun degree6() {
        _degree6.value = _degree6.value != true
    }

    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt().let {
                when (it) {
                    0 -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertIntToString(value: Int): String {
        return value.toString()
    }

//    @InverseMethod("convertLongToString")
//    fun convertStringToLong(value: String): Long {
//        return try {
//            value.toLong().let {
//                when (it) {
//                    0L -> 1
//                    else -> it
//                }
//            }
//        } catch (e: NumberFormatException) {
//            1
//        }
//    }
//
//    fun convertLongToString(value: Long): String {
//        return value.toString()
//    }
}

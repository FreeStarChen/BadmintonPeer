package com.mark.badmintonpeer.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.Filter
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class FilterViewModel(private val repository: BadmintonPeerRepository) : ViewModel() {

    // Handle leave login
    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    private val _filter = MutableLiveData<Filter>()
    val filter: LiveData<Filter>
        get() = _filter

    private val _morningTime = MutableLiveData<Boolean>()
    val morningTime: LiveData<Boolean>
        get() = _morningTime

    private val _afternoonTime = MutableLiveData<Boolean>()
    val afternoonTime: LiveData<Boolean>
        get() = _afternoonTime

    private val _nightTime = MutableLiveData<Boolean>()
    val nightTime: LiveData<Boolean>
        get() = _nightTime

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

    fun leave() {
        _leave.value = true
    }

    fun onLeaveCompleted() {
        _leave.value = null
    }

    fun setMorningTime() {
        _morningTime.value = _morningTime.value != true
    }

    fun setAfternoonTime() {
        _afternoonTime.value = _afternoonTime.value != true
    }

    fun setNightTime() {
        _nightTime.value = _nightTime.value != true
    }

    fun setDegree1() {
        _degree1.value = _degree1.value != true
    }

    fun setDegree2() {
        _degree2.value = _degree2.value != true
    }

    fun setDegree3() {
        _degree3.value = _degree3.value != true
    }

    fun setDegree4() {
        _degree4.value = _degree4.value != true
    }

    fun setDegree5() {
        _degree5.value = _degree5.value != true
    }

    fun setDegree6() {
        _degree6.value = _degree6.value != true
    }

    fun resetMorningTime() {
        _morningTime.value = false
    }

    fun resetAfternoonTime() {
        _afternoonTime.value = false
    }

    fun resetNightTime() {
        _nightTime.value = false
    }

    fun resetDegree1() {
        _degree1.value = false
    }

    fun resetDegree2() {
        _degree2.value = false
    }

    fun resetDegree3() {
        _degree3.value = false
    }

    fun resetDegree4() {
        _degree4.value = false
    }

    fun resetDegree5() {
        _degree5.value = false
    }

    fun resetDegree6() {
        _degree6.value = false
    }




}
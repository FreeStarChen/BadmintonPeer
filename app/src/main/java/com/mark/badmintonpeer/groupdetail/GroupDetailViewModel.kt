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
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.util.TimeCalculator

class GroupDetailViewModel(val argument: Group,private val repository: BadmintonPeerRepository) : ViewModel() {


    private val _group = MutableLiveData<Group>().apply {
        value = argument
    }
    val group: LiveData<Group>
    get() = _group

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
                    MainApplication.instance.resources.getDimensionPixelSize(R.dimen.space_detail_circle)
            }
        }
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

    val groupDate = _group.value?.date?.let { TimeCalculator.getDate(it.time) }
    val groupStartTime = _group.value?.startTime?.let { TimeCalculator.getTime(it.time) }
    val groupEndTime = _group.value?.startTime?.let { TimeCalculator.getTime(it.time) }
    val groupPrice = "$${_group.value?.price}"
}
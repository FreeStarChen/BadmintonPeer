package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.data.Filter
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.group.GroupViewModel
import java.lang.IllegalArgumentException


/**
 * Factory for filter item ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class FilterViewModelFactory(
    private val filter: Filter?,
    private val repository: BadmintonPeerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(GroupViewModel::class.java) ->
                    GroupViewModel(filter, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
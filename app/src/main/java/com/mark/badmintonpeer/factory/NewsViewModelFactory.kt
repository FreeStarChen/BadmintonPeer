package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.data.News
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.newsdetail.NewsDetailViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val news: News,
    private val repository: BadmintonPeerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(NewsDetailViewModel::class.java) ->
                    NewsDetailViewModel(news, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

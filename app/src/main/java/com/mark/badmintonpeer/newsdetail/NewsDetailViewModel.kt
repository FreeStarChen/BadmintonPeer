package com.mark.badmintonpeer.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.News
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.util.TimeCalculator

class NewsDetailViewModel(val argument: News, private val repository: BadmintonPeerRepository) :
    ViewModel() {

    private val _news = MutableLiveData<News>().apply {
        value = argument
    }
    val news: LiveData<News>
        get() = _news

    val newsPostTime = _news.value?.postTime?.let { TimeCalculator.getDateAndYear(it.time) }

}
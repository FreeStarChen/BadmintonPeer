package com.mark.badmintonpeer.record

import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository

class RecordTypeViewModel(val type: String, val repository: BadmintonPeerRepository) : ViewModel() {
}
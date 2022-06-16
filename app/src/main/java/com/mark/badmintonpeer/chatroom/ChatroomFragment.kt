package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mark.badmintonpeer.R

class ChatroomFragment : Fragment() {

    companion object {
        fun newInstance() = ChatroomFragment()
    }

    private lateinit var viewModel: ChatroomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chatroom_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatroomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
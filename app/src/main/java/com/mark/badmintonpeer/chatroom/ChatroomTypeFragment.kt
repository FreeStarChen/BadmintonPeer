package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mark.badmintonpeer.R

class ChatroomTypeFragment : Fragment() {

    companion object {
        fun newInstance() = ChatroomTypeFragment()
    }

    private lateinit var viewModel: ChatroomTypeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chatroom_type_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatroomTypeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
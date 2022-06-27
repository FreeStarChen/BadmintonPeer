package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.ChatroomTypeFragmentBinding
import com.mark.badmintonpeer.databinding.GroupTypeFragmentBinding
import com.mark.badmintonpeer.group.GroupTypeFragment

class ChatroomTypeFragment() : Fragment() {

    private lateinit var viewModel: ChatroomTypeViewModel

    companion object {
        fun newInstance(type: String): ChatroomTypeFragment {
            val fragment = ChatroomTypeFragment()
            val args = Bundle()
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }
    }



    fun getType(): String {
        return requireArguments().getString("type","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ChatroomTypeFragmentBinding.inflate(inflater)
        return binding.root
    }

}
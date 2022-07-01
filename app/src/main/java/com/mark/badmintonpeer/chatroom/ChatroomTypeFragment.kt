package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.ChatroomTypeFragmentBinding
import com.mark.badmintonpeer.databinding.GroupTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.group.GroupTypeFragment

class ChatroomTypeFragment(private val type: String) : Fragment() {

    private val viewModel by viewModels<ChatroomViewModel> {getVmFactory(type)}

    companion object {
        fun newInstance(type: String): ChatroomTypeFragment {
            val fragment = ChatroomTypeFragment(type)
//            val args = Bundle()
//            args.putString("type", type)
//            fragment.arguments = args
            return fragment
        }
    }



//    fun getType(): String {
//        return requireArguments().getString("type","")
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ChatroomTypeFragmentBinding.inflate(inflater)
        return binding.root
    }

}
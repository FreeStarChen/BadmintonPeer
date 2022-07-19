package com.mark.badmintonpeer.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.chatroom.ChatroomTypeFragment
import com.mark.badmintonpeer.chatroom.ChatroomTypeViewModel
import com.mark.badmintonpeer.databinding.ChatroomTypeFragmentBinding
import com.mark.badmintonpeer.databinding.RecordTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory

class RecordTypeFragment : Fragment() {

    private val viewModel by viewModels<RecordTypeViewModel> {getVmFactory(getType())}

    private lateinit var binding: RecordTypeFragmentBinding

    companion object {
        fun newInstance(type: String): RecordTypeFragment {
            val fragment = RecordTypeFragment()
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

        binding = RecordTypeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

}
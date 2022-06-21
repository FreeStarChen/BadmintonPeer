package com.mark.badmintonpeer.groupdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupDetailFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory

class GroupDetailFragment : Fragment() {

    private val viewModel by viewModels<GroupDetailViewModel> {
        getVmFactory(
            GroupDetailFragmentArgs.fromBundle(
                requireArguments()
            ).groupKey
        )
    }

    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupDetailFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }



}
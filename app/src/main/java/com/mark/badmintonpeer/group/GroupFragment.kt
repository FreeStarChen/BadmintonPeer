package com.mark.badmintonpeer.group

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupFragmentBinding

class GroupFragment : Fragment() {

    companion object {
        fun newInstance() = GroupFragment()
    }

    private lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater)

        binding.imageAddGroup.setOnClickListener {
            this.findNavController().navigate(NavigationDirections.navigateToCreateGroupFragment())
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
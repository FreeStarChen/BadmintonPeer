package com.mark.badmintonpeer.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.creategroup.CreateGroupViewModel
import com.mark.badmintonpeer.databinding.CreateGroupFragmentBinding
import com.mark.badmintonpeer.databinding.FilterFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker

class FilterFragment : Fragment() {

    private val viewModel by viewModels<FilterViewModel> {getVmFactory()}

    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FilterFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextDate.transformIntoDatePicker(requireContext(),"MM/dd/yyyy")

        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToGroupFragment())
        }

        return binding.root
    }

}
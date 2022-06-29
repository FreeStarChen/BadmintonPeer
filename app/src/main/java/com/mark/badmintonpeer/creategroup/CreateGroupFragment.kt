package com.mark.badmintonpeer.creategroup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.MainActivity
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.databinding.CreateGroupFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoTimePicker
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CreateGroupFragment : Fragment() {

    private val viewModel by viewModels<CreateGroupViewModel> {getVmFactory()}

    companion object {
        fun newInstance() = CreateGroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val binding = CreateGroupFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        binding.editTextStartTime.transformIntoTimePicker(requireContext(),"HH:mm")
        binding.editTextEndTime.transformIntoTimePicker(requireContext(),"HH:mm")

        viewModel.leave.observe(viewLifecycleOwner) {
            it?.let { needRefresh ->
                if (needRefresh) {
                    ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
                        refresh()
                    }
                }
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.group.value?.classification = radioGroup.findViewById<RadioButton>(i).text.toString()
            Timber.d("classification=${radioGroup.findViewById<RadioButton>(i).text.toString()}")
        }

        return binding.root
    }

    fun callViewModelAddGroupResult() {
        Timber.d("aaa1")
        viewModel.addGroupResult()
        Timber.d("aaa2")

    }

}
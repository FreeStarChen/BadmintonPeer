package com.mark.badmintonpeer.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.FilterDialogBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker
import timber.log.Timber
import java.util.*

class FilterDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<FilterViewModel> { getVmFactory() }
    private lateinit var binding: FilterDialogBinding

    companion object {
        fun newInstance() = FilterDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FilterDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FilterDialogBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextDate.transformIntoDatePicker(requireContext(), "yyyy/MM/dd")

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities,
            R.layout.spinner_item_gray
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerCities.adapter = adapter

        }

        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    val selectedCity = binding.spinnerCities.selectedItem.toString()
                    Timber.d("selectedCity=$selectedCity")

//                    viewModel.city.value = selectedCity
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.towns,
            R.layout.spinner_item_gray
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerTowns.adapter = adapter

        }

        binding.spinnerTowns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    val selectedTown = binding.spinnerTowns.selectedItem.toString()
                    Timber.d("selectedCity=$selectedTown")

//                    viewModel.city.value = selectedCity
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToGroupFragment())
        }

        viewModel.leave.observe(viewLifecycleOwner) {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
        }

        val wantTimes = mutableListOf<Date>()
        val wantDegrees = mutableListOf<String>()

        viewModel.morningTime.observe(viewLifecycleOwner) {
            if (it) {
//                wantTimes.add()

            } else {
//                wantTimes.remove()
            }
        }

        return binding.root
    }

}
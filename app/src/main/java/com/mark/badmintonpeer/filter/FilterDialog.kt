package com.mark.badmintonpeer.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Filter
import com.mark.badmintonpeer.databinding.FilterDialogBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.CitiesAndTowns
import com.mark.badmintonpeer.util.TimeCalculator.toDateLong
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker
import timber.log.Timber
import java.sql.Timestamp

class FilterDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<FilterViewModel> { getVmFactory() }
    private lateinit var binding: FilterDialogBinding

    private var currentCity: String = "選擇縣市"
    private var currentTown: String = "選擇地區"

    private lateinit var filter: Filter

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

        val adapterCity = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            CitiesAndTowns.getAllCitiesName()
        )

        binding.spinnerCities.adapter = adapterCity

        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                currentCity = binding.spinnerCities.selectedItem.toString()
                setSpinnerTown()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spinnerTowns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentTown = binding.spinnerTowns.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        setDefaultCityWithTown()


//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.towns,
//            R.layout.spinner_item_gray
//        ).also { adapter ->
//            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
//            binding.spinnerTowns.adapter = adapter
//
//        }
//
//        binding.spinnerTowns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                if (p2 != 0) {
//                    val selectedTown = binding.spinnerTowns.selectedItem.toString()
//                    Timber.d("selectedCity=$selectedTown")
//
////                    viewModel.city.value = selectedCity
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }

        viewModel.leave.observe(viewLifecycleOwner) {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
        }

        val wantPeriods = mutableListOf<String>()
        val wantDegrees = mutableListOf<String>()

        viewModel.morningTime.observe(viewLifecycleOwner) {
            if (it) {
                wantPeriods.add("早上")

            } else {
                wantPeriods.remove("早上")
            }
        }

        viewModel.afternoonTime.observe(viewLifecycleOwner) {
            if (it) {
                wantPeriods.add("下午")
            } else {
                wantPeriods.remove("下午")
            }
        }

        viewModel.nightTime.observe(viewLifecycleOwner) {
            if (it) {
                wantPeriods.add("晚上")
            } else {
                wantPeriods.remove("晚上")
            }
        }

        viewModel.degree1.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("新手")
            } else {
                wantDegrees.remove("新手")
            }
        }

        viewModel.degree2.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("初階")
            } else {
                wantDegrees.remove("初階")
            }
        }

        viewModel.degree3.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("初中")
            } else {
                wantDegrees.remove("初中")
            }
        }

        viewModel.degree4.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("中階")
            } else {
                wantDegrees.remove("中階")
            }
        }

        viewModel.degree5.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("中上")
            } else {
                wantDegrees.remove("中上")
            }
        }

        viewModel.degree6.observe(viewLifecycleOwner) {
            if (it) {
                wantDegrees.add("高階")
            } else {
                wantDegrees.remove("高階")
            }
        }

        binding.buttonFilter.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            Timber.d("date = $date")
            when {
                currentCity == "選擇縣市" -> {
                    Toast.makeText(context,"請選擇縣市",Toast.LENGTH_SHORT).show()
                }
                wantPeriods.isEmpty() -> {
                    Toast.makeText(context,"請選擇時間",Toast.LENGTH_SHORT).show()
                }
                wantDegrees.isEmpty() -> {
                    Toast.makeText(context,"請選擇需求程度",Toast.LENGTH_SHORT).show()
                }
                else -> {

                    val dateToLong: Long = if (date == "") {
                        0L
                    }else {
                        date.toDateLong()
                    }

                    Timber.d("dateToLong = $dateToLong")
                    val priceLow: Int = if (binding.editTextPriceLow.text.toString() == "") {
                        0
                    } else {
                        binding.editTextPriceLow.text.toString().toInt()
                    }

                    val priceHigh: Int = if (binding.editTextPriceHigh.text.toString() == "") {
                        99999999
                    } else {
                        binding.editTextPriceHigh.text.toString().toInt()
                    }

                    filter = Filter(
                        currentCity,
                        currentTown,
                        Timestamp(dateToLong),
                        wantPeriods,
                        wantDegrees,
                        priceLow,
                        priceHigh
                    )
                    Timber.d("filter = $filter")
                    findNavController().navigate(NavigationDirections.navigateToGroupFragment(filter))
                }
            }


        }

        return binding.root
    }

    private fun setDefaultCityWithTown() {
        binding.spinnerCities.setSelection(CitiesAndTowns.getCityIndexByName(currentCity))
        setSpinnerTown()
    }

    private fun setSpinnerTown() {
        val adapterTown = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                CitiesAndTowns.getTownsByCityName(currentCity)
            )
        }
        binding.spinnerTowns.adapter = adapterTown
        binding.spinnerTowns.setSelection(
            CitiesAndTowns.getTownIndexByName(
                currentCity,
                currentTown
            )
        )
    }


}
package com.mark.badmintonpeer.creategroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.CreateGroupFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.TimeCalculator.toDateLong
import com.mark.badmintonpeer.util.TimeCalculator.toTimeLong
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoTimePicker
import timber.log.Timber
import java.sql.Timestamp

class CreateGroupFragment : Fragment() {

    private val viewModel by viewModels<CreateGroupViewModel> {getVmFactory()}
    lateinit var binding: CreateGroupFragmentBinding

    companion object {
        fun newInstance() = CreateGroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CreateGroupFragmentBinding.inflate(inflater)

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

        val characteristics = mutableListOf<String>()
        val degree = mutableListOf<String>()

        viewModel.haveWaterDispenser.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintWaterDispenser.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("飲水機")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintWaterDispenser.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("飲水機")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveAirCondition.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintAirCondition.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("冷氣")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintAirCondition.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("冷氣")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.havePuGround.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintPuGround.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("PU地面")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintPuGround.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("PU地面")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveSpotlight.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintSpotlight.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("側面燈光")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintSpotlight.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("側面燈光")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveShower.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintShower.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("淋浴間")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintShower.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("淋浴間")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveParking.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintParking.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("停車場")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintParking.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("停車場")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveCutlery.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintCutlery.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("餐飲販售")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintCutlery.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("餐飲販售")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveHairDryer.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintHairDryer.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("吹風機")
                viewModel.group.value?.characteristic = characteristics
            }else {
                binding.constraintHairDryer.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("吹風機")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.degree1.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree1.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("新手")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree1.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("新手")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree2.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree2.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("初階")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree2.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("初階")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree3.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree3.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("初中")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree3.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("初中")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree4.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree4.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("中階")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree4.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("中階")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree5.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree5.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("中上")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree5.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("中上")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree6.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree6.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("高階")
                viewModel.group.value?.degree = degree
            }else {
                binding.constraintDegree6.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("高階")
                viewModel.group.value?.degree = degree
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.group.value?.classification = radioGroup.findViewById<RadioButton>(i).text.toString()
            Timber.d("classification=${radioGroup.findViewById<RadioButton>(i).text.toString()}")
        }

        return binding.root
    }

    fun getDatePickerDialogDateAndTime() {
        Timber.d("date=${binding.editTextDate.text.toString()}")
        val date = binding.editTextDate.text.toString()
        val dateToLong = date.toDateLong()
        viewModel.group.value?.date = Timestamp(dateToLong)
        val startTime = binding.editTextStartTime.text.toString()
        val startTimeToLong = startTime.toTimeLong()
        viewModel.group.value?.startTime = Timestamp(startTimeToLong)
        val endTime = binding.editTextStartTime.text.toString()
        val endTimeToLong = endTime.toTimeLong()
        viewModel.group.value?.endTime = Timestamp(endTimeToLong)
    }

    fun callViewModelAddGroupResult() {
        getDatePickerDialogDateAndTime()
        viewModel.addGroupResult()
    }

}
package com.mark.badmintonpeer.groupdetail

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.chatroom.ChatroomFragmentDirections
import com.mark.badmintonpeer.databinding.GroupDetailFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import timber.log.Timber

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

        binding.recyclerViewDetailImages.adapter = GroupDetailImageAdapter()
        binding.recyclerViewDetailCircles.adapter = GroupDetailCircleAdapter()

        val degreeAdapter = GroupDetailDegreeAdapter()
        binding.recyclerViewDetailDegree.adapter = degreeAdapter

        viewModel.degree.observe(viewLifecycleOwner) {
            degreeAdapter.submitList(it)
            Timber.d("degree=$it")
        }

        val characteristicAdapter = GroupDetailCharacteristicAdapter()
        binding.recyclerViewDetailCharacteristic.adapter = characteristicAdapter

        viewModel.characteristic.observe(viewLifecycleOwner) {
            characteristicAdapter.submitList(it)
            Timber.d("characteristic=$it")
        }

        val linearSnapHelper = LinearSnapHelper().apply {
            attachToRecyclerView(binding.recyclerViewDetailImages)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.recyclerViewDetailImages.setOnScrollChangeListener { _, _, _, _, _ ->
                viewModel.onImageScrollChange(
                    binding.recyclerViewDetailImages.layoutManager, linearSnapHelper
                )
            }
        }

        // set the initial position to the center of infinite image
        viewModel.group.value?.let { group ->
            binding.recyclerViewDetailImages.scrollToPosition(group.images.size * 100)

            viewModel.snapPosition.observe(viewLifecycleOwner) {
                (binding.recyclerViewDetailCircles.adapter as GroupDetailCircleAdapter).selectedPosition.value =
                    (it % group.images.size)
            }
        }

        binding.buttonDetailSignUp.setOnClickListener {
            viewModel.addGroupMemberResult()
            viewModel.subtractNeedPeopleNumberResult()
            findNavController().navigateUp()
        }

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

        binding.imageDetailChat.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToChatroomFragment())
        }

        return binding.root
    }


}
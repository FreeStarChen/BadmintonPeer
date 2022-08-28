package com.mark.badmintonpeer.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.databinding.ProfileFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.group.GroupTypeAdapter

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }
    private lateinit var binding: ProfileFragmentBinding

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerViewProfile.adapter = GroupTypeAdapter(
            GroupTypeAdapter.OnClickListener {
                viewModel.navigateToGroupDetail(it)
            }
        )

        viewModel.navigateToGroupDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToGroupDetailFragment(it))
                viewModel.onGroupDetailNavigated()
            }
        }

        viewModel.groups.observe(viewLifecycleOwner) {
            (binding.recyclerViewProfile.adapter as GroupTypeAdapter).submitList(it)
        }

        binding.constraintTemporaryGroupRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecordFragment("零打"))
        }

        binding.constraintSeasonGroupRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecordFragment("季打"))
        }

        binding.constraintClassRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecordFragment("課程"))
        }

        binding.constraintCompetitionRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecordFragment("比賽"))
        }

        return binding.root
    }
}

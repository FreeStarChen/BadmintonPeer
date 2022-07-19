package com.mark.badmintonpeer.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.ProfileFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.group.GroupTypeAdapter
import com.mark.badmintonpeer.login.UserManager
import timber.log.Timber

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }
    private lateinit var binding: ProfileFragmentBinding

    companion object {
        fun newInstance() = ProfileFragment()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

        binding.constraintGroupRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecordFragment())
        }





        return binding.root
    }

}
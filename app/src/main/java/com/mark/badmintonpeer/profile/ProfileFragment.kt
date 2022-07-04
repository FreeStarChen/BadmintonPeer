package com.mark.badmintonpeer.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.ProfileFragmentBinding
import com.mark.badmintonpeer.login.UserManager
import timber.log.Timber

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()

    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater)

        return binding.root
    }

}
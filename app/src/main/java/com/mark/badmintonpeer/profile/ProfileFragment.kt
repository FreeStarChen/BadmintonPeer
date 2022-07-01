package com.mark.badmintonpeer.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupDetailFragmentBinding
import com.mark.badmintonpeer.databinding.ProfileFragmentBinding
import timber.log.Timber

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()

        //        const val TAG = MainActivity::class.java.simpleName
        const val RC_SIGN_IN = 100
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("693739849566-a0guvtjhnl3sp39l837njclnvnjckqsf.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email
                val token = account?.idToken

                Timber.tag("givemepass").i("email:$email, token:$token")
                Toast.makeText(
                    requireContext(),
                    getString(R.string.login_success),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: ApiException) {
                Timber.tag("givemepass").i("signInResult:failed code=%s", e.statusCode)
                Toast.makeText(requireContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Timber.tag("givemepass").i("login fail")
            Toast.makeText(requireContext(), getString(R.string.login_fail), Toast.LENGTH_SHORT)
                .show()
        }
    }

}
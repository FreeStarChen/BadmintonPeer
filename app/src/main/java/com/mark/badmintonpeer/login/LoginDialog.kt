package com.mark.badmintonpeer.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.ktx.Firebase
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.User
import com.mark.badmintonpeer.databinding.LoginDialogBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.ext.setTouchDelegate
import com.mark.badmintonpeer.profile.ProfileFragment
import timber.log.Timber

class LoginDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: LoginDialogBinding

    companion object {
        const val RC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LoginDialogBinding.inflate(inflater)
        binding.layoutLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_up))

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.buttonLoginClose.setTouchDelegate()

        binding.signInButton.setOnClickListener {
            signIn()
        }

        viewModel.leave.observe(viewLifecycleOwner) {
            it?.let {
                dismiss()
                viewModel.onLeaveCompleted()
            }
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
                val picture = account?.photoUrl.toString()
                val id = account?.id
                val name = account?.displayName

                UserManager.userToken = token
                UserManager.userId = id
                Timber.d("userToken=${token}")

               viewModel.userFromGooglelogin.value =
                   id?.let { id ->
                       name?.let { name ->
                           email?.let { email ->
                               User(id, name, email,"",picture,"", listOf("")) }
                       }
                   }
                Timber.d("viewModel.userFromGooglelogin.value=${viewModel.userFromGooglelogin.value}")

                viewModel.checkUserResult()

                Timber.tag("givemepass").i("email:$email, token:$token, picture:$picture, id:$id, name:$name")
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

//        dismiss()
    }




}
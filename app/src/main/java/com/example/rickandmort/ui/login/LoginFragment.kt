package com.example.rickandmort.ui.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.rickandmort.R
import com.example.rickandmort.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: LoginFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSingUp.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (view !=null) {
            when(view.id) {
                R.id.btnSingUp -> {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
                R.id.btnLogin -> {
                    logInRegisteredUser()

                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.loginEmail.text.toString().trim { it <= ' ' }) -> {
                view?.let { Snackbar.make(it,resources.getString(R.string.err_msg_enter_email), Snackbar.LENGTH_SHORT).show() }
                false
            }
            TextUtils.isEmpty(binding.loginPassword.text.toString().trim { it <= ' ' }) -> {
                view?.let { Snackbar.make(it,resources.getString(R.string.err_msg_enter_password), Snackbar.LENGTH_SHORT).show() }
                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            showProgressBar()

            val email = binding.loginEmail.text.toString().trim { it <= ' ' }
            val password = binding.loginPassword.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    hideProgressBar()

                    if (task.isSuccessful) {
                        view?.let { Snackbar.make(it,resources.getString(R.string.msg_success), Snackbar.LENGTH_SHORT).show() }
                        findNavController().navigate(R.id.action_loginFragment_to_navHomeFragment)


                    } else {
                        view?.let { Snackbar.make(it,task.exception!!.message.toString(), Snackbar.LENGTH_SHORT).show() }
                    }
                }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar5.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        binding.progressBar5.visibility = View.VISIBLE

    }

}
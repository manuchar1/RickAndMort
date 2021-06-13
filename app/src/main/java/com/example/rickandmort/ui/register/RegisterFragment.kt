package com.example.rickandmort.ui.register

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.rickandmort.R
import com.example.rickandmort.databinding.RegisterFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterFragment : Fragment(){

    private var binding: RegisterFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRegister?.setOnClickListener {
            registerUser()
        }
        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun validateRegisterDetails(): Boolean {

       // binding?.apply {

            return when {

                TextUtils.isEmpty(binding?.etFirstName?.text.toString().trim { it <= ' ' }) -> {

                    Toast.makeText(requireContext(), "Enter Name!", Toast.LENGTH_LONG).show()

                    /*showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)*/
                    false
                }

                TextUtils.isEmpty(binding?.etLastName?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireContext(), "Enter Last Name!", Toast.LENGTH_LONG).show()
                    false
                }

                TextUtils.isEmpty(binding?.etEmail?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireContext(), "Enter Email!", Toast.LENGTH_LONG).show()
                    false
                }

                TextUtils.isEmpty(binding?.etPassword?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireContext(), "Enter Password!", Toast.LENGTH_LONG).show()
                    false
                }

                TextUtils.isEmpty(binding?.etPassword?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(requireContext(), "Enter Name!", Toast.LENGTH_LONG).show()
                    false
                }

                binding?.etPassword?.text.toString().trim { it <= ' ' } != binding?.etConfirmPassword?.text.toString()
                    .trim { it <= ' ' } -> {

                    Toast.makeText(
                        requireContext(),
                        "password and confirm password mismatch!",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                }

                else -> {

                   /* Toast.makeText(requireContext(), "Your details are valid!", Toast.LENGTH_LONG)
                        .show()*/
                    true
                }
            }
    }

    private fun registerUser() {

        if (validateRegisterDetails()) {

            showProgressBar()

            val email: String = binding?.etEmail?.text.toString().trim { it <= ' ' }
            val password: String = binding?.etPassword?.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        hideProgressBar()

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            FirebaseAuth.getInstance().signOut()

                            view?.let { Snackbar.make(it,"You are registered successfully. Your user id is ${firebaseUser.uid}", Snackbar.LENGTH_SHORT).show() }

                            FirebaseAuth.getInstance().signOut()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)


                        } else {
                            view?.let { Snackbar.make(it,task.exception!!.message.toString(), Snackbar.LENGTH_SHORT).show() }
                        }
                    })
        }
    }

    private fun hideProgressBar() {
        binding?.progressBar3?.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        binding?.progressBar3?.visibility = View.VISIBLE

    }

}


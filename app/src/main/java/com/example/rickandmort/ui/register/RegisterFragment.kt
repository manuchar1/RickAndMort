package com.example.rickandmort.ui.register

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rickandmort.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

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
            validateRegisterDetails()
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
                    //showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                    false
                }

                else -> {


                    Toast.makeText(requireContext(), "Your details are valid!", Toast.LENGTH_LONG)
                        .show()

                    //showErrorSnackBar("Your details are valid.", false)
                    true
                }
            }


        //}


    }

}


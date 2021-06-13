package com.example.rickandmort.base

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.rickandmort.R
import com.example.rickandmort.databinding.DialogProgressBinding
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog:Dialog




    private lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.base_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("ShowToast")
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {



        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).setAnchorView(requireView()) }


    }

    fun Fragment.showProgressDialog(text: String) {
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogProgressBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)



        dialogBinding.tvProgressText.text = text
    //    mProgressDialog.setCancelable(false)
//        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }



}
package com.example.rickandmort.base

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.rickandmort.R
import com.google.android.material.snackbar.Snackbar

class BaseFragment : Fragment() {




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


/*            Snackbar.make(getv, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()*/

    }

}
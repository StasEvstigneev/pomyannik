package com.example.prayforthem.listings_for_repose.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.listings.ui.ListingsFragmentDirections
import com.example.prayforthem.listings_for_health.ui.ForHealthFragment
import com.example.prayforthem.listings_for_repose.presentation.ForReposeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForReposeFragment : ForHealthFragment() {

    override val viewModel by viewModel<ForReposeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigate()
        }

    }

    private fun navigate() {
        val action = ListingsFragmentDirections.actionListsFragmentToCreateListFragment(
            isForHealthArg = IS_FOR_HEALTH
        )
        findNavController().navigate(action)

    }


    companion object {
        fun newInstance() = ForReposeFragment()
        private const val IS_FOR_HEALTH = false
    }

}
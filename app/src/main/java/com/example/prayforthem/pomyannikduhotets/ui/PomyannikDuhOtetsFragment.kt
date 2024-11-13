package com.example.prayforthem.pomyannikduhotets.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.pomyannikduhotets.presentation.PomyannikDuhOtetsViewModel
import com.example.prayforthem.prayeraddnames.ui.PrayerAddNamesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PomyannikDuhOtetsFragment : PrayerAddNamesFragment() {

    override val viewModel by viewModel<PomyannikDuhOtetsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.title = getString(R.string.pomyannik_add_duh_otets)
            notificationText.text = getString(R.string.you_can_add_duh_otets_into_prayer)
            btnToPrayer.text = getString(R.string.next)
        }

        binding.btnAddName.setOnClickListener {
            findNavController().navigate(R.id.action_pomyannikAddDuhOtetsFragment_to_namesFragment)
        }

        binding.btnAddList.setOnClickListener {
            val action = PomyannikDuhOtetsFragmentDirections
                .actionPomyannikAddDuhOtetsFragmentToChooseListingFragment(IS_FOR_HEALTH)
            findNavController().navigate(action)
        }

        binding.btnToPrayer.setOnClickListener {
            val action = PomyannikDuhOtetsFragmentDirections
                .actionPomyannikAddDuhOtetsFragmentToPomyannikRoditeliFragment()
            val exitMessage =
                if (areNamesAdded) getString(R.string.navigate_forward) else getString(R.string.you_have_not_added_names_go_ahead)

            showGoForwardDialog(action, exitMessage)
        }
    }

    companion object {
        private const val IS_FOR_HEALTH = true
    }

}
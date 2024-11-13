package com.example.prayforthem.pomyannikusopsrod.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.pomyannikusopsrod.presentation.PomyannikUsopSrodViewModel
import com.example.prayforthem.prayeraddnames.ui.PrayerAddNamesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PomyannikUsopSrodFragment : PrayerAddNamesFragment() {

    override val viewModel by viewModel<PomyannikUsopSrodViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.title = getString(R.string.pomyannik_add_usop_relatives)
            notificationText.text = getString(R.string.you_can_add_usop_relatives_into_prayer)
        }

        binding.btnAddName.setOnClickListener {
            findNavController().navigate(R.id.action_pomyannikUsopSrodFragment_to_namesFragment)
        }

        binding.btnAddList.setOnClickListener {
            val action = PomyannikUsopSrodFragmentDirections
                .actionPomyannikUsopSrodFragmentToChooseListingFragment(IS_FOR_HEALTH)
            findNavController().navigate(action)
        }

        binding.btnToPrayer.setOnClickListener {
            val action = PomyannikUsopSrodFragmentDirections
                .actionPomyannikUsopSrodFragmentToPomyannikDisplayFragment(POMYANNIK_FILE_NAME)
            val exitMessage =
                if (areNamesAdded) getString(R.string.navigate_to_prayer)
                else getString(R.string.you_have_not_added_names)
            showGoForwardDialog(action, exitMessage)
        }

    }

    companion object {
        private const val IS_FOR_HEALTH = false
        private const val POMYANNIK_FILE_NAME = "pomyannik"
    }
}
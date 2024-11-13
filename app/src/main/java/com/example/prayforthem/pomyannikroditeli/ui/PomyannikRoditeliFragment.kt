package com.example.prayforthem.pomyannikroditeli.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.pomyannikroditeli.presentation.PomyannikRoditeliViewModel
import com.example.prayforthem.prayeraddnames.ui.PrayerAddNamesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PomyannikRoditeliFragment : PrayerAddNamesFragment() {

    override val viewModel by viewModel<PomyannikRoditeliViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.title = getString(R.string.pomyannik_add_parents)
            notificationText.text = getString(R.string.you_can_add_parents_into_prayer)
            btnToPrayer.text = getString(R.string.next)
        }

        binding.btnAddName.setOnClickListener {
            findNavController().navigate(R.id.action_pomyannikRoditeliFragment_to_namesFragment)
        }

        binding.btnAddList.setOnClickListener {
            val action = PomyannikRoditeliFragmentDirections
                .actionPomyannikRoditeliFragmentToChooseListingFragment(IS_FOR_HEALTH)
            findNavController().navigate(action)
        }

        binding.btnToPrayer.setOnClickListener {
            val action = PomyannikRoditeliFragmentDirections
                .actionPomyannikRoditeliFragmentToPomyannikSrodnikiFragment()
            val exitMessage =
                if (areNamesAdded) getString(R.string.navigate_forward)
                else getString(R.string.you_have_not_added_names_go_ahead)
            showGoForwardDialog(action, exitMessage)
        }
    }

    companion object {
        private const val IS_FOR_HEALTH = true
    }

}
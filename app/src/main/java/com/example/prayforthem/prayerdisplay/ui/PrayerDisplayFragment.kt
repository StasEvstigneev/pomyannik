package com.example.prayforthem.prayerdisplay.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentPrayerDisplayBinding
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import com.example.prayforthem.prayerdisplay.presentation.PrayerDisplayViewModel
import com.example.prayforthem.utils.DialogConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PrayerDisplayFragment : Fragment() {

    private var _binding: FragmentPrayerDisplayBinding? = null
    private val binding get() = _binding!!
    private val args: PrayerDisplayFragmentArgs by navArgs()
    private val viewModel: PrayerDisplayViewModel by viewModel {
        parametersOf(args.prayerFileNameArg)
    }
    private lateinit var exitDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayerDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitDialog = DialogConstructor.createExitDialog(
            context = requireContext(),
            action = {
                viewModel.clearTempNames()
                findNavController().popBackStack()
            },
            message = getString(R.string.are_you_sure_you_want_to_close_prayer),
            view = binding.overlay
        )

        binding.toolbar.setNavigationOnClickListener {
            exitDialog.show()
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitDialog.show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: PrayersDisplayScreenState) {
        when (state) {
            is PrayersDisplayScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    prayer.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayersDisplayScreenState.Content -> {
                binding.apply {
                    progressBar.isVisible = false
                    prayerTitle.text = state.title
                    prayerText.text = state.text
                    prayer.isVisible = true
                    placeholder.isVisible = false
                }
            }

            is PrayersDisplayScreenState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    prayer.isVisible = false
                    placeholder.isVisible = true
                }
            }

        }
    }

}
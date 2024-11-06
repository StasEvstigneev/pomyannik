package com.example.prayforthem.prayeraddnames.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.prayforthem.databinding.FragmentPrayerAddNamesBinding
import com.example.prayforthem.prayeraddnames.domain.PrayerAddNamesScreenState
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PrayerAddNamesFragment : Fragment() {

    private var _binding: FragmentPrayerAddNamesBinding? = null
    private val binding get() = _binding!!
    private val args: PrayerAddNamesFragmentArgs by navArgs()
    private val viewModel: PrayerAddNamesViewModel by viewModel {
        parametersOf(args.prayerFileNameArg, args.forHealthArg)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayerAddNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: PrayerAddNamesScreenState) {
        when (state) {
            is PrayerAddNamesScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayerAddNamesScreenState.Content -> {
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    placeholder.isVisible = state.list.isEmpty()
                }
            }

        }
    }

}
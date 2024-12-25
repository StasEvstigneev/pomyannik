package com.example.prayforthem.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.App
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentSettingsBinding
import com.example.prayforthem.settings.domain.models.SettingsScreenState
import com.example.prayforthem.settings.domain.models.ThemeSettings
import com.example.prayforthem.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.themeSettings.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.system_theme -> {
                    viewModel.changeTheme(ThemeSettings.SYSTEM)
                    (activity?.applicationContext as App).switchTheme(ThemeSettings.SYSTEM.themeCode)
                }

                R.id.light_theme -> {
                    viewModel.changeTheme(ThemeSettings.LIGHT)
                    (activity?.applicationContext as App).switchTheme(ThemeSettings.LIGHT.themeCode)
                }

                R.id.dark_theme -> {
                    viewModel.changeTheme(ThemeSettings.DARK)
                    (activity?.applicationContext as App).switchTheme(ThemeSettings.DARK.themeCode)
                }
            }
        }

        binding.userNames.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToCustomNamesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: SettingsScreenState) {
        when (state) {
            is SettingsScreenState.Loading -> {
                binding.progressBar.isVisible = true
            }

            is SettingsScreenState.Content -> {
                binding.progressBar.isVisible = false
                when (state.themeSettings) {
                    ThemeSettings.SYSTEM -> binding.systemTheme.isChecked = true
                    ThemeSettings.LIGHT -> binding.lightTheme.isChecked = true
                    ThemeSettings.DARK -> binding.darkTheme.isChecked = true
                }
            }

        }
    }


}
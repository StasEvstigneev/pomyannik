package com.example.prayforthem.prayers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.prayforthem.databinding.FragmentPrayersBinding
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrayersFragment : Fragment() {

    private var _binding: FragmentPrayersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PrayersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
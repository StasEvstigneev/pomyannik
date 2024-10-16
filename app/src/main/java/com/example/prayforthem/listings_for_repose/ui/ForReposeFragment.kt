package com.example.prayforthem.listings_for_repose.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.prayforthem.databinding.FragmentForReposeBinding
import com.example.prayforthem.listings_for_repose.repository.ForReposeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForReposeFragment : Fragment() {

    private var _binding: FragmentForReposeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForReposeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForReposeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ForReposeFragment()
        private const val IS_FOR_HEALTH = false
    }

}